package com.microsservices.rabbitmq_tutorials.tut4_routing_messages;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile({"tut4", "routing"})
@Configuration
public class Tut4Config {

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("tut.direct");
    }

    @Profile("sender")
    @Bean
    public Tut4Sender sender() {
        return new Tut4Sender();
    }

    @Profile("receiver")
    public static class ReceiverConfig {

        @Bean
        public Queue logsQueue(){
            return new AnonymousQueue();
        }

        @Bean
        public Queue criticalLogsQueue(){
            return new AnonymousQueue();
        }

        @Bean
        public Binding bindWarnLogs(DirectExchange direct, Queue logsQueue) {
            return BindingBuilder.bind(logsQueue).to(direct).with("warn");
        }

        @Bean
        public Binding bindDebugLogs(DirectExchange direct, Queue logsQueue) {
            return BindingBuilder.bind(logsQueue).to(direct).with("debug");
        }

        @Bean
        public Binding bindErrorToLogsQueue(DirectExchange direct, Queue logsQueue) {
            return BindingBuilder.bind(logsQueue).to(direct).with("error");
        }

        @Bean
        public Binding bindErrorToCriticalLogsQueue(DirectExchange direct, Queue criticalLogsQueue) {
            return BindingBuilder.bind(criticalLogsQueue).to(direct).with("error");
        }

        @Profile("receiver")
        @Bean
        public Tut4Receiver receiver() {
            return new Tut4Receiver();
        }
    }
}
