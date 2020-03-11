package com.wzn.blog.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.wzn.ablog.common.vo.Result;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import io.lettuce.core.RedisCommandTimeoutException;
import io.lettuce.core.RedisConnectionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange serverWebExchange, Throwable throwable) {
        log.info("GLOBAL EXCEPTION:{}, \n{}", serverWebExchange.getRequest().getPath(), throwable);
        Result result = new Result();
        if (throwable instanceof NullPointerException) {
            result.setStatus("500").setMessage("请登录后操作");
        } else if (throwable instanceof SignatureException) {
            result.setStatus("500").setMessage("token错误");
        } else if(throwable instanceof ExpiredJwtException){
            result.setStatus("500").setMessage("身份过期请重新登录");
        }else{
            result.setStatus("500").setMessage("服务异常");
        }

        DataBufferFactory bufferFactory = serverWebExchange.getResponse().bufferFactory();
        serverWebExchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);
        return serverWebExchange.getResponse().writeWith(
                Flux.just(bufferFactory.wrap(JSON.toJSONString(result).getBytes())));
    }
}
