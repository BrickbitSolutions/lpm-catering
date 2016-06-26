package be.brickbit.lpm.catering.service.task;

import be.brickbit.lpm.catering.domain.OrderStatus;
import be.brickbit.lpm.catering.domain.PreparationTask;
import be.brickbit.lpm.catering.exceptions.EntityNotFoundException;
import be.brickbit.lpm.catering.fixture.PreparationTaskFixture;
import be.brickbit.lpm.catering.fixture.UserFixture;
import be.brickbit.lpm.catering.repository.PreparationTaskRepository;
import be.brickbit.lpm.core.domain.User;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TaskServiceImplTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private PreparationTaskRepository preparationTaskRepository;

    @InjectMocks
    private TaskService taskService = new TaskServiceImpl();

    @Test
    public void updateTaskWithStatus() throws Exception {
        PreparationTask preparationTask = PreparationTaskFixture.mutable();
        preparationTask.setId(1L);
        User user = UserFixture.getCateringAdmin();

        when(preparationTaskRepository.findOne(preparationTask.getId())).thenReturn(preparationTask);

        taskService.updateTaskWithStatus(1L, OrderStatus.IN_PROGRESS, user);

        assertThat(preparationTask.getStartTime()).isNotNull();
        assertThat(preparationTask.getUserId()).isEqualTo(user.getId());

        verify(preparationTaskRepository, times(1)).save(preparationTask);
    }

    @Test
    public void updateTaskWithStatus__invalidTask() throws Exception {
        expectedException.expect(EntityNotFoundException.class);
        expectedException.expectMessage("Task 1 does not exist in the database");

        when(preparationTaskRepository.findOne(1L)).thenReturn(null);

        taskService.updateTaskWithStatus(1L, OrderStatus.IN_PROGRESS, UserFixture.getCateringAdmin());
    }

}