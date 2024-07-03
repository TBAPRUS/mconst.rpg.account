package mconst.rpg.accounts.models.requests;

import lombok.Data;

@Data
public class AccountSpendRequest {
    private Integer money;
    private String token;
}
