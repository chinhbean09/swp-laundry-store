package com.project.thelaundry.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/base")
@RequiredArgsConstructor
@Tag(name = "Base", description = "UnAuthorization APIs")
public class BaseController {
}
