package br.com.sysmap.bootcamp.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

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

}