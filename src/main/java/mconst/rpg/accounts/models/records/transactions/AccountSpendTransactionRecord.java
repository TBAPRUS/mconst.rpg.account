package mconst.rpg.accounts.models.records.transactions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountSpendTransactionRecord {
    private Integer id;
    private Integer money;
    private TransactionRecordStatus status;
}
