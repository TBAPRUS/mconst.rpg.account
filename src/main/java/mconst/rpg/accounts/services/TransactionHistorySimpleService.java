package mconst.rpg.accounts.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@Slf4j
@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TransactionHistorySimpleService<T> implements TransactionHistoryService<T> {
    Map<String, T> transactions;

    public TransactionHistorySimpleService() {
        transactions = new HashMap<>();
    }

    public synchronized void sync(Runnable runnable) {
        runnable.run();
    }

    @Override
    public void set(String token, T data) {
        transactions.put(token, data);
    }

    @Override
    public void delete(String token) {
        transactions.remove(token);
    }

    @Override
    public T get(String token) {
        return transactions.get(token);
    }

    @Override
    public Boolean contains(String token) {
        return transactions.containsKey(token);
    }
}
