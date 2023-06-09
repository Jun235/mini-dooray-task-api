package com.nhnacademy.minidooraytaskapi.tag.repository;

import com.nhnacademy.minidooraytaskapi.get_tag.entity.GetTag;
import com.nhnacademy.minidooraytaskapi.milestone.entity.Milestone;
import com.nhnacademy.minidooraytaskapi.project.entity.Project;
import com.nhnacademy.minidooraytaskapi.project_status.entity.ProjectStatus;
import com.nhnacademy.minidooraytaskapi.tag.dto.TagDto;
import com.nhnacademy.minidooraytaskapi.tag.entity.Tag;
import com.nhnacademy.minidooraytaskapi.task.entity.Task;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED)
@DisplayName("Tag : Repository 테스트")
class TagRepositoryTest {
    @Autowired
    TestEntityManager testEntityManager;
    @Autowired
    TagRepository tagRepository;

    @Test
    @DisplayName("프로젝트에 해당하는 테크 가져오는 Repository")
    void getTagByProjectId() {
        Tag tag = new Tag();
        Tag tag2 = new Tag();

        Project project = new Project();
        project.setName("ggg");
        ProjectStatus projectStatus = new ProjectStatus();
        projectStatus.setName("test");
        project.setProjectStatus(projectStatus);
        Milestone milestone = new Milestone();
        milestone.setProject(project);
        milestone.setName("test");

        testEntityManager.persist(projectStatus);
        testEntityManager.persist(project);
        testEntityManager.persist(milestone);

        tag.setName("test1");
        tag.setProject(project);
        tag2.setProject(project);
        tag2.setName("test2");

        testEntityManager.persist(tag);
        testEntityManager.persist(tag2);

        List<TagDto> allTag = tagRepository.getTagByProjectId(project.getProjectId());

        Assertions.assertThat(allTag).hasSize(2);
        Assertions.assertThat(allTag.get(0).getTagId()).isEqualTo(tag.getTagId());
    }

    @Test
    @DisplayName("해당 프로젝트의 특정 업무의 태그 가져오기")
    void getTask() {
        Tag tag = new Tag();
        Tag tag2 = new Tag();

        Project project = new Project();
        project.setName("ggg");
        ProjectStatus projectStatus = new ProjectStatus();
        projectStatus.setName("test");
        project.setProjectStatus(projectStatus);
        Milestone milestone = new Milestone();
        milestone.setProject(project);
        milestone.setName("test");

        testEntityManager.persist(projectStatus);
        testEntityManager.persist(project);
        testEntityManager.persist(milestone);

        Task task = new Task();
        Task task2 = new Task();

        task.setTitle("test");
        task.setTaskWriterMemberId("naht94");
        task.setProject(project);
        task.setMilestone(milestone);
        task2.setTitle("test");
        task2.setTaskWriterMemberId("naht94");
        task2.setProject(project);
        task2.setMilestone(milestone);

        testEntityManager.persist(task);
        testEntityManager.persist(task2);

        tag.setName("test1");
        tag.setProject(project);
        tag2.setName("test2");
        tag2.setProject(project);

        testEntityManager.persist(tag);
        testEntityManager.persist(tag2);

        GetTag getTag = new GetTag();
        GetTag getTag2 = new GetTag();

        getTag.setTag(tag);
        getTag.setTask(task);
        getTag.setPk(new GetTag.Pk(tag.getTagId(), task.getTaskId()));
        getTag2.setTag(tag2);
        getTag2.setTask(task);
        getTag2.setPk(new GetTag.Pk(tag2.getTagId(), task2.getTaskId()));

        testEntityManager.persist(getTag);
        testEntityManager.persist(getTag2);

        List<TagDto> allTag = tagRepository.getTagByProjectIdAndTaskId(project.getProjectId(), task.getTaskId());

        Assertions.assertThat(allTag).hasSize(2);
        Assertions.assertThat(allTag.get(0).getTagId()).isEqualTo(tag.getTagId());
    }
}