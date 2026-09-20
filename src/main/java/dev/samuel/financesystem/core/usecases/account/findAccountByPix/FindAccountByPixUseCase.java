package dev.samuel.financesystem.core.usecases.account.findAccountByPix;

import dev.samuel.financesystem.core.entities.Account;

public interface FindAccountByPixUseCase {

    Account execute(String pix);
}
