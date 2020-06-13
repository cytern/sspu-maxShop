package com.funong.funong.timeTask;



import com.funong.funong.pojo.Token;
import com.funong.funong.service.TokenService;
import com.funong.funong.service.WebSocketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Configuration
@EnableScheduling//可以在启动类上注解也可以在当前文件
public class ClearToken {
    @Autowired
    private TokenService tokenService;
    private Logger logger = LoggerFactory.getLogger(WebSocketService.class);
    @Autowired
    private WebSocketService webSocketService;
    @Scheduled(cron = " 0 0 10,14,16 * * ?")
    public void task() throws ParseException {
        List<Token> tokens = tokenService.getAllToken();
        Date date =new Date();
        SimpleDateFormat myFmt2=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      for (Token token:tokens){

         String tokenDate1 = token.getUpdateTime();

         Date tokenDate = myFmt2.parse(tokenDate1);
         long bTime = tokenDate.getTime();
         long eTime = date.getTime();
         long lTime = eTime - bTime;
         double days = (double) (lTime/(1000*60*60*24));
         if (days >1){
              String tokensa = token.getToken();
              tokenService.deleteTokenByToken(tokensa);
         }
      }
    }
}
