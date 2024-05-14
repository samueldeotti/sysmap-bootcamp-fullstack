package br.com.sysmap.bootcamp.config;

import br.com.sysmap.bootcamp.domain.listeners.WalletListener;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * The type Rabbit config.
 */
@Configuration
public class RabbitConfig {

  /**
   * Rest template rest template.
   *
   * @return the rest template
   */
  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }

  /**
   * Wallet queue queue.
   *
   * @return the queue
   */
  @Bean
  public Queue WalletQueue() {
    return new Queue("WalletQueue");
  }

  /**
   * Receiver wallet listener.
   *
   * @return the wallet listener
   */
  @Bean
  public WalletListener receiver() {
    return new WalletListener();
  }

  /**
   * Converter simple message converter.
   *
   * @return the simple message converter
   */
  @Bean
  public SimpleMessageConverter converter() {
    SimpleMessageConverter converter = new SimpleMessageConverter();
    converter.setAllowedListPatterns(List.of("br.com.sysmap.bootcamp.dto.*", "java.util.*"));
    return converter;
  }

}