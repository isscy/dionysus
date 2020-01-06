package cn.ff.dionysus.core.auth.controller;

import cn.ff.dionysus.common.basal.entity.R;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("current.to")
    private String todo;

    @GetMapping("config")
    public R testConfig1() {
        return R.ok(todo);
    }
}
