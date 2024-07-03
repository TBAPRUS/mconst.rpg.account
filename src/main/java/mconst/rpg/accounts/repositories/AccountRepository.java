package mconst.rpg.accounts.repositories;

import com.baeldung.jooq.introduction.db.public_.tables.Accounts;
import mconst.rpg.accounts.models.exceptions.ConflictException;
import mconst.rpg.accounts.models.exceptions.ExceptionBody;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AccountRepository {
    public Optional<Integer> subMoney(Integer id, Integer money, DSLContext trx) {
        var currentMoney = trx
                .update(Accounts.ACCOUNTS)
                .set(Accounts.ACCOUNTS.MONEY, Accounts.ACCOUNTS.MONEY.sub(money))
                .where(Accounts.ACCOUNTS.ID.eq(id))
                .returning(Accounts.ACCOUNTS.MONEY)
                .fetchOptional(Accounts.ACCOUNTS.MONEY);
        if (currentMoney.isPresent() && currentMoney.get() < 0) {
            var exceptionBody = new ExceptionBody();
            exceptionBody.addError("Not enough money", "account", "money");
            throw new ConflictException(exceptionBody);
        }
        return currentMoney;
    }

    public Optional<Integer> addMoney(Integer id, Integer money, DSLContext trx) {
        return trx
                .update(Accounts.ACCOUNTS)
                .set(Accounts.ACCOUNTS.MONEY, Accounts.ACCOUNTS.MONEY.add(money))
                .where(Accounts.ACCOUNTS.ID.eq(id))
                .returning(Accounts.ACCOUNTS.MONEY)
                .fetchOptional(Accounts.ACCOUNTS.MONEY);
    }
}
