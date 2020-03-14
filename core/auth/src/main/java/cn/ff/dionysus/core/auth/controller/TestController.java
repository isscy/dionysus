package cn.ff.dionysus.core.auth.controller;

import cn.ff.dionysus.common.basal.entity.R;
import cn.ff.dionysus.core.auth.config.properties.TestProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * _
 *
 * @author fengfan 2020/1/6
 */
@RestController
@RequestMapping("test")
public class TestController {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private TestProperty testProperty;

    @Value("${current.to}")
    private String todo;

    @GetMapping("config")
    public R testConfig1() {
        redisTemplate.opsForValue().set("to", todo);
        return R.ok(redisTemplate.opsForValue().get("to"));
    }
    @GetMapping("config2")
    public R testConfig2() {

        return R.ok(testProperty.toString());
    }

}
