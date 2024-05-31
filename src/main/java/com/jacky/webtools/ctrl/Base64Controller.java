package com.jacky.webtools.ctrl;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Tag(name = "Base64")
@RestController
@RequestMapping("/base64")
public class Base64Controller {

    @Operation(summary = "编码")
    @PostMapping(value = "encode", produces = "application/text", consumes = "application/text")
    public String encode(@RequestBody String content) {
        return Base64.getEncoder().encodeToString(content.getBytes(StandardCharsets.UTF_8));
    }

    @Operation(summary = "解码")
    @PostMapping(value = "decode", produces = "application/text", consumes = "application/text")
    public String decode(@RequestBody String content) {
        return new String(Base64.getDecoder().decode(content.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
    }
}
