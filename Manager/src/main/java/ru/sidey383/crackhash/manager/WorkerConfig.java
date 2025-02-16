package ru.sidey383.crackhash.manager;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "worker")
public class WorkerConfig {

    public WorkerConfig() {}

    private List<String> address;

}
