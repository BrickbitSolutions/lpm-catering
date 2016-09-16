package be.brickbit.lpm.catering.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import be.brickbit.lpm.catering.service.wallet.WalletService;
import be.brickbit.lpm.catering.service.wallet.command.EditWalletAmountCommand;
import be.brickbit.lpm.catering.service.wallet.dto.WalletDto;
import be.brickbit.lpm.catering.service.wallet.mapper.WalletDtoMapper;
import be.brickbit.lpm.infrastructure.AbstractController;

@RequestMapping(value = "/user/wallet")
@RestController
public class WalletController extends AbstractController {

    @Autowired
    private WalletService walletService;

    @Autowired
    private WalletDtoMapper walletDtoMapper;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize(value = "hasRole('USER')")
    public WalletDto getWallet() {
        return walletService.findByUserId(getCurrentUser().getId(), walletDtoMapper);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize(value = "hasAnyRole('ADMIN', 'CATERING_ADMIN')")
    public void addAmountToWallet(@RequestBody @Valid EditWalletAmountCommand walletCommand) {
        walletService.addAmount(getCurrentUser().getId(), walletCommand.getAmount());
    }

    @RequestMapping(value = "/substract", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize(value = "hasRole('USER')")
    public void substractAmountFromWallet(@RequestBody @Valid EditWalletAmountCommand walletCommand) {
        walletService.substractAmount(getCurrentUser().getId(), walletCommand.getAmount());
    }
}
