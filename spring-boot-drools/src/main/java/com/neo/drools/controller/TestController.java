package com.neo.drools.controller;

import com.neo.drools.enums.DroolsEnum;
import com.neo.drools.model.Address;
import com.neo.drools.model.fact.AddressCheckResult;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;


@RequestMapping("/test")
@Controller
public class TestController {


    @Resource
    Map<String, KieSession> kieSessionMap;


    @ResponseBody
    @RequestMapping("1/address")
    public void test1(int num) {
        Address address = new Address();
        address.setPostcode(generateRandom(num));
        KieSession kieSession = kieSessionMap.get(DroolsEnum.DROOLS_ENUM1_0.getRulesName());

        AddressCheckResult result = new AddressCheckResult();
        kieSession.insert(address);
        kieSession.insert(result);
        int ruleFiredCount = kieSession.fireAllRules();
        kieSession.destroy();
        System.out.println("触发了" + ruleFiredCount + "条规则");

        if (result.isPostCodeResult()) {
            System.out.println("规则校验通过");
        }

    }

    @ResponseBody
    @RequestMapping("2/address")
    public void test2(int num) {
        Address address = new Address();
        address.setPostcode(generateRandom(num));
        KieSession kieSession = kieSessionMap.get(DroolsEnum.DROOLS_ENUM2_0.getRulesName());

        AddressCheckResult result = new AddressCheckResult();
        kieSession.insert(address);
        kieSession.insert(result);
        int ruleFiredCount = kieSession.fireAllRules();
        kieSession.destroy();
        System.out.println("触发了" + ruleFiredCount + "条规则");

        if (result.isPostCodeResult()) {
            System.out.println("规则校验通过");
        }

    }

    /**
     * 生成随机数
     *
     * @param num
     * @return
     */
    public String generateRandom(int num) {
        String chars = "0123456789";
        StringBuffer number = new StringBuffer();
        for (int i = 0; i < num; i++) {
            int rand = (int) (Math.random() * 10);
            number = number.append(chars.charAt(rand));
        }
        return number.toString();
    }
}
