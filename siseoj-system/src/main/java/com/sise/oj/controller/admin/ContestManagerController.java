package com.sise.oj.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.oj.base.ResultJson;
import com.sise.oj.domain.Contest;
import com.sise.oj.domain.ContestProblem;
import com.sise.oj.domain.Problem;
import com.sise.oj.domain.dto.ContestProblemListDto;
import com.sise.oj.domain.param.QueryParam;
import com.sise.oj.service.ContestProblemService;
import com.sise.oj.service.ContestService;
import com.sise.oj.util.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Cijee
 * @version 1.0
 */
@RestController
@RequiredArgsConstructor
@Api(tags = "比赛管理接口")
@RequestMapping("/admin/contest")
public class ContestManagerController {

    private final ContestService contestService;
    private final ContestProblemService contestProblemService;

    @PreAuthorize("@el.check('contest:list')")
    @ApiOperation("分页查询比赛")
    @GetMapping
    public ResultJson<Page<Contest>> list(Page<Contest> page, QueryParam param) {
        return ResultJson.success(contestService.list(page, param));
    }

    @PreAuthorize("@el.check('contest:list')")
    @ApiOperation("查询比赛详情")
    @GetMapping("/{id}")
    public ResultJson<Contest> get(@PathVariable Long id) {
        return ResultJson.success(contestService.findById(id));
    }

    @PreAuthorize("@el.check('contest:add')")
    @ApiOperation("新增比赛")
    @PostMapping
    public ResultJson<?> add(@Validated(Contest.Create.class) @RequestBody Contest contest) {
        // 获取当前用户的id和用户名
        contest.setUid(SecurityUtils.getCurrentUserId());
        contest.setAuthor(SecurityUtils.getCurrentUsername());
        // 设置默认不封榜
        if (contest.getSealRank() == null) contest.setSealRank(false);
        // 设置默认比赛类型：普通比赛
        if (contest.getType() == null) contest.setType(0);
        // 设置默认比赛来源：原创
        if (contest.getSource() == null) contest.setSource(0L);
        // 默认未开始
        contest.setStatus(-1);
        contestService.insert(contest);
        return ResultJson.success(null);
    }

    @PreAuthorize("@el.check('contest:edit')")
    @ApiOperation("更新比赛")
    @PutMapping
    public ResultJson<?> edit(@Validated(Contest.Update.class) @RequestBody Contest contest) {
        contestService.update(contest);
        return ResultJson.success(null);
    }

    @PreAuthorize("@el.check('contest:del')")
    @ApiOperation("删除比赛")
    @DeleteMapping
    public ResultJson<?> delete(Long id) {
        contestService.delete(id);
        return ResultJson.success(null);
    }

    @PreAuthorize("@el.check('contest:edit')")
    @ApiOperation("更新比赛题目列表")
    @PutMapping("/problem-list")
    public ResultJson<?> editContestProblemList(@RequestBody ContestProblemListDto contestProblemListDto) {
        contestService.updateContestProblemList(contestProblemListDto.getContestProblems(), contestProblemListDto.getCid());
        return ResultJson.success(null);
    }

    @PreAuthorize("@el.check('contest:list')")
    @ApiOperation("查询比赛题目列表")
    @GetMapping("/problem-list")
    public ResultJson<List<ContestProblem>> contestProblemList(@RequestParam Long cid) {
        return ResultJson.success(contestProblemService.findByContestId(cid));
    }

    @PreAuthorize("@el.check('contest:list')")
    @ApiOperation("查询比赛题目信息")
    @GetMapping("/{id}/problems")
    public ResultJson<Page<Problem>> getProblemList(@PathVariable Long id, Page<Problem> page, QueryParam param) {
        // 设置比赛题目ID
        param.setId(id);
        return ResultJson.success(contestService.getProblemList(page, param));
    }
}
