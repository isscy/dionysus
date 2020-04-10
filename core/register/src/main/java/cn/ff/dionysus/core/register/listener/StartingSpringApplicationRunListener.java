package cn.ff.dionysus.core.register.listener;

import cn.ff.dionysus.core.register.constant.NacosConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.concurrent.ScheduledExecutorService;

/**
 * _
 *
 * @author fengfan 2020/3/20
 */

public class StartingSpringApplicationRunListener implements SpringApplicationRunListener, Ordered {
    private static final Logger LOGGER = LoggerFactory.getLogger(StartingSpringApplicationRunListener.class);
    private final SpringApplication application;
    private final String[] args;
    private volatile boolean isStarting = false;
    private ScheduledExecutorService scheduledExecutorService;


    public StartingSpringApplicationRunListener(SpringApplication application, String[] args) {
        this.application = application;
        this.args = args;
    }
    public void starting() {
        this.isStarting = true;
    }


    public void environmentPrepared(ConfigurableEnvironment environment) {
        if (NacosConstant.STANDALONE_MODE) {
            System.setProperty("nacos.mode", "standalone");
        } else {
            System.setProperty("nacos.mode", "cluster");
        }
        System.setProperty("nacos.function.mode", "All");


        System.setProperty("nacos.local.ip", NacosConstant.LOCAL_IP);
    }

    public void contextPrepared(ConfigurableApplicationContext context) {
        this.logClusterConf();
        this.logStarting();
    }

    public void contextLoaded(ConfigurableApplicationContext context) {
    }

    public void started(ConfigurableApplicationContext context) {
        this.isStarting = false;
        if (this.scheduledExecutorService != null) {
            this.scheduledExecutorService.shutdownNow();
        }

        this.logFilePath();
        LOGGER.info("Nacos started successfully in {} mode.", System.getProperty("nacos.mode"));
    }

    public void running(ConfigurableApplicationContext context) {
    }

    public void failed(ConfigurableApplicationContext context, Throwable exception) {
        this.isStarting = false;
        this.logFilePath();
        //LOGGER.error("Nacos failed to start, please see {}/logs/nacos.log for more details.", SystemUtils.NACOS_HOME);
    }

    public int getOrder() {
        return -2147483648;
    }

    private void logClusterConf() {
        /*if (!SystemUtils.STANDALONE_MODE) {
            try {
                List<String> clusterConf = SystemUtils.readClusterConf();
                LOGGER.info("The server IP list of Nacos is {}", clusterConf);
            } catch (IOException var2) {
                LOGGER.error("read cluster conf fail", var2);
            }
        }*/

    }

    private void logFilePath() {
       /* LOGGER.info("Nacos Log files: {}/logs/", SystemUtils.NACOS_HOME);
        LOGGER.info("Nacos Conf files: {}/conf/", SystemUtils.NACOS_HOME);
        LOGGER.info("Nacos Data files: {}/data/", SystemUtils.NACOS_HOME);*/
    }

    private void logStarting() {
        /*if (!SystemUtils.STANDALONE_MODE) {
            this.scheduledExecutorService = new ScheduledThreadPoolExecutor(1, new ThreadFactory() {
                public Thread newThread(Runnable r) {
                    Thread thread = new Thread(r, "nacos-starting");
                    thread.setDaemon(true);
                    return thread;
                }
            });
            this.scheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
                public void run() {
                    if (StartingSpringApplicationRunListener.this.isStarting) {
                        StartingSpringApplicationRunListener.LOGGER.info("Nacos is starting...");
                    }

                }
            }, 1L, 1L, TimeUnit.SECONDS);
        }*/

    }

}
