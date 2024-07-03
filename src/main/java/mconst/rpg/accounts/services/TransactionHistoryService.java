package mconst.rpg.accounts.services;

public interface TransactionHistoryService<T> {
    void set(String token, T data);
    void delete(String token);
    T get(String token);
    Boolean contains(String token);
    void sync(Runnable runnable);
}
