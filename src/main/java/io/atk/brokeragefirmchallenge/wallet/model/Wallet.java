package io.atk.brokeragefirmchallenge.wallet.model;

import io.atk.brokeragefirmchallenge.model.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class Wallet extends Entity {
    private String accountId;
    private List<WalletOperation> operations;
}
