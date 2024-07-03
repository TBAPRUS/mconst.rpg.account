package mconst.rpg.accounts.services;

import mconst.rpg.accounts.models.exceptions.ConflictException;
import mconst.rpg.accounts.models.exceptions.ExceptionBody;
import mconst.rpg.accounts.models.exceptions.NotFoundException;
import mconst.rpg.accounts.models.records.transactions.AccountSpendTransactionRecord;
import mconst.rpg.accounts.models.records.transactions.TransactionRecordStatus;
import mconst.rpg.accounts.repositories.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final TransactionHistoryService<AccountSpendTransactionRecord> transactionHistoryService;
    private final TransactionService transactionService;

    public AccountService(
            AccountRepository accountRepository,
            TransactionHistoryService<AccountSpendTransactionRecord> transactionHistoryService,
            TransactionService transactionService
    ) {
        this.accountRepository = accountRepository;
        this.transactionHistoryService = transactionHistoryService;
        this.transactionService = transactionService;
    }

    public void spend(Integer id, Integer money, String token) {
        transactionHistoryService.sync(() -> {
            if (transactionHistoryService.contains(token)) {
                var exceptionBody = new ExceptionBody();
                exceptionBody.addError("Transaction already exists", "transaction");
                throw new ConflictException(exceptionBody);
            }

            transactionService.transaction(trx -> {
                var transactionRecord = new AccountSpendTransactionRecord(id, money, TransactionRecordStatus.COMMITED);
                accountRepository.subMoney(id, money, trx);
                transactionHistoryService.set(token, transactionRecord);
            });
        });
    }

    public void compensateSpending(String token) {
        transactionHistoryService.sync(() -> {
            if (!transactionHistoryService.contains(token)) {
                var exceptionBody = new ExceptionBody();
                exceptionBody.addError("Not found", "token");
                throw new NotFoundException(exceptionBody);
            }

            var transactionRecord = transactionHistoryService.get(token);
            if (transactionRecord.getStatus() != TransactionRecordStatus.COMMITED) {
                var exceptionBody = new ExceptionBody();
                exceptionBody.addError("Transaction isn't commited", "transaction", "status");
                throw new ConflictException(exceptionBody);
            }

            transactionService.transaction(trx -> {
                var newTransactionRecord = new AccountSpendTransactionRecord(
                        transactionRecord.getId(),
                        transactionRecord.getMoney(),
                        TransactionRecordStatus.COMPENSATED
                );
                accountRepository.addMoney(transactionRecord.getId(), transactionRecord.getMoney(), trx);
                transactionHistoryService.set(token, newTransactionRecord);
            });
        });
    }
}
