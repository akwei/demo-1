package com.github.akwei.demo1;

import com.github.akwei.demo1.api.UserApi;
import com.github.akwei.demo1.api.model.ApiSessionInput;
import com.github.akwei.demo1.api.model.ApiSessionToken;
import com.github.akwei.demo1.api.model.ApiUser;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.Instant;

@Api(tags = "User")
@RestController
public class UserCtrl implements UserApi {

    @Resource
    private JwtDecoder jwtDecoder;

    @Resource
    private JwtEncoder jwtEncoder;

    @Override
    public ResponseEntity<ApiSessionToken> login(ApiSessionInput body) {
        ApiSessionToken sessionToken = new ApiSessionToken();
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .id("userid")
                .subject("userSubject")
                .issuer("akwei")
                .issuedAt(Instant.now())
                .claim("key1", 1)
                .claim("key2", 2)
                .build();
        Jwt jwt = jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet));
        sessionToken.setToken(jwt.getTokenValue());
        return ResponseEntity.ok(sessionToken);
    }

    @Override
    public ResponseEntity<ApiUser> getUsers(String age) {
        if (StringUtils.equals(age, "err")) {
            BizException bizException = new BizException();
            bizException.setHttpStatus(HttpStatus.FORBIDDEN);
            throw bizException;
        }
        ApiUser apiUser = new ApiUser();
        apiUser.setId(1219L);
        apiUser.setName("akwei");
        return ResponseEntity.ok(apiUser);
    }
}
