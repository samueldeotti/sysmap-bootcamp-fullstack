package br.com.sysmap.bootcamp.domain.listeners;


import br.com.sysmap.bootcamp.domain.service.WalletService;
import br.com.sysmap.bootcamp.dto.WalletDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * The type Wallet listener.
 */
@Slf4j
@RabbitListener(queues = "WalletQueue")
public class WalletListener {

  @Autowired
  private WalletService walletService;

  /**
   * Receive.
   *
   * @param walletDto the wallet dto
   */
  @RabbitHandler
  public void receive(WalletDto walletDto) {
    walletService.debit(walletDto);
  }


}