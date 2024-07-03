package mconst.rpg.accounts.services;

import org.jooq.DSLContext;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
public class TransactionService {
    private DSLContext create;

    public TransactionService(DSLContext create) {
        this.create = create;
    }

    public void transaction(Consumer<DSLContext> transaction) {
        create.transaction(trx -> {
            transaction.accept(create);
        });
    }
}
