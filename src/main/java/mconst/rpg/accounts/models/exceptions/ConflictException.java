package mconst.rpg.accounts.models.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConflictException extends RuntimeException {
    private ExceptionBody body;
}
