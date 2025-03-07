//package com.scgecommerce.global.filter;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.scgecommerce.global.security.util.MyEncoder;
//import java.nio.charset.StandardCharsets;
//import java.util.HashMap;
//import java.util.Map;
//import org.springframework.cloud.gateway.filter.GatewayFilter;
//import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
//import org.springframework.cloud.gateway.filter.factory.rewrite.CachedBodyOutputMessage;
//import org.springframework.cloud.gateway.support.BodyInserterContext;
//import org.springframework.core.io.buffer.DataBuffer;
//import org.springframework.core.io.buffer.DataBufferUtils;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StreamUtils;
//import org.springframework.web.reactive.function.BodyInserter;
//import org.springframework.web.reactive.function.BodyInserters;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//@Component
//public class SignupFilter extends AbstractGatewayFilterFactory<SignupFilter.Config> {
//
//  private final MyEncoder myEncoder;
//  private final PasswordEncoder passwordEncoder;
//
//  public SignupFilter(MyEncoder myEncoder, PasswordEncoder passwordEncoder) {
//    super(Config.class);
//    this.myEncoder = myEncoder;
//    this.passwordEncoder = passwordEncoder;
//  }
//
//  @Override
//  public GatewayFilter apply(Config config) {
//    return ((exchange, chain) -> {
//      Flux<DataBuffer> requestBody = exchange.getRequest().getBody();
//      return DataBufferUtils.join(requestBody)
//          .flatMap(dataBuffer -> {
//            byte[] bytes = new byte[dataBuffer.readableByteCount()];
//            dataBuffer.read(bytes);
//            DataBufferUtils.release(dataBuffer);
////            String check = new String(bytes, StandardCharsets.UTF_8);
//            Map modifiedMap = new HashMap();
//            modifiedMap.put("test1","test1");
//            ObjectMapper mapper = new ObjectMapper();
//            String stringmap;
//            try {
//              stringmap = mapper.writeValueAsString(modifiedMap);
//            } catch (JsonProcessingException e) {
//              throw new RuntimeException(e);
//            }
//            BodyInserter bodyInserter = BodyInserters.fromPublisher(Mono.just(stringmap), String.class);
//            HttpHeaders headers = new HttpHeaders();
//            headers.putAll(exchange.getRequest().getHeaders());
//            headers.remove(HttpHeaders.CONTENT_LENGTH);
//            CachedBodyOutputMessage outputMessage = new CachedBodyOutputMessage(exchange, headers);
//            return bodyInserter.insert(outputMessage, new BodyInserterContext())
//                .then(Mono.defer(() -> {
//                  ServerHttpRequestDecorator decorator = new ServerHttpRequestDecorator(
//                      exchange.getRequest()) {
//                    @Override
//                    public HttpHeaders getHeaders() {
//                      long contentLength = headers.getContentLength();
//                      HttpHeaders httpHeaders = new HttpHeaders();
//                      httpHeaders.putAll(super.getHeaders());
//                      if (contentLength > 0) {
//                        httpHeaders.setContentLength(contentLength);
//                      } else {
//                        httpHeaders.set(HttpHeaders.TRANSFER_ENCODING, "chunked");
//                      }
//                      return httpHeaders;
//                    }
//
//                    @Override
//                    public Flux getBody() {
//                      return outputMessage.getBody();
//                    }
//                  };
//                  return chain.filter(exchange.mutate().request(decorator).build());
//                }));
//
//          });
//
//
//    });
//  }
//
//  public static class Config {
//    // 설정을 위한 클래스, 필요에 따라 필드 추가
//  }
//}
