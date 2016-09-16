package be.brickbit.lpm.catering.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import be.brickbit.lpm.catering.domain.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Wallet findByUserId(Long userId);
}
