package com.sise.oj.controller.admin;

import com.sise.oj.base.ResultJson;
import com.sise.oj.service.ProblemCountService;
import com.sise.oj.service.ProblemService;
import com.sise.oj.service.UserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Cijee
 * @version 1.0
 */
@RestController
@RequiredArgsConstructor
@Api(tags = "后台：系统接口")
@RequestMapping("/admin/system")
public class SystemController {

    private final UserService userService;
    private final ProblemCountService problemCountService;
    private final ProblemService problemService;

    @GetMapping
    public ResultJson<Map<String, Object>> getSystemInfo() {
        int userCount = userService.count();
        int problemCount = problemService.count();
        int acceptCount = problemCountService.acceptCount();
        int totalCount = problemCountService.totalCount();
        Map<String, Object> map = new HashMap<String, Object>(4) {{
           put("userCount", userCount);
           put("problemCount", problemCount);
           put("acceptCount", acceptCount);
           put("totalCount", totalCount);
        }};
        return ResultJson.success(map);
    }
}
