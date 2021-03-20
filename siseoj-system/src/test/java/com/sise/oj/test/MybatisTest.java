package com.sise.oj.test;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.oj.domain.Judge;
import com.sise.oj.domain.Problem;
import com.sise.oj.domain.Tag;
import com.sise.oj.mapper.JudgeMapper;
import com.sise.oj.mapper.TagMapper;
import com.sise.oj.service.ProblemService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Cijee
 * @version 1.0
 */
@SpringBootTest
public class MybatisTest {

    @Resource
    private ProblemService problemService;

    @Resource
    private JudgeMapper judgeMapper;

    @Resource
    private TagMapper tagMapper;

    @Test
    public void getOne() {
        Problem problem = problemService.getOne(Wrappers.lambdaQuery(Problem.class).eq(Problem::getPid, 1), false);
        System.out.println(problem);
    }

    @Test
    public void findByPage() {
        IPage<Tag> page = new Page<>(2, 2);
        page = tagMapper.selectPage(page, null);
        List<Tag> records = page.getRecords();
        records.forEach(System.out::println);
    }

    @Test
    public void update() {
        judgeMapper.update(null, Wrappers.lambdaUpdate(Judge.class)
                .eq(Judge::getId, 1L).set(Judge::getTime, 3).set(Judge::getMemory, 5320));
    }
}
