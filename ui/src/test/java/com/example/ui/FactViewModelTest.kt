package com.example.ui

import com.example.domain.GetRandomFactUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.RegisterExtension
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@ExtendWith(MockitoExtension::class)
class FactViewModelTest {

    // The MainCoroutineRule is all you need for testing StateFlow/State
    @JvmField
    @RegisterExtension
    val mainCoroutineRule = MainCoroutineRule()

    @Mock
    private lateinit var getRandomFactUseCase: GetRandomFactUseCase
    private lateinit var viewModel: FactViewModel

    @BeforeEach
    fun setup() {

    }

    @Test
    fun `init SHOULD fetch random fact and update state`() {
        // Arrange
        val expectedFact = "test fact from mock"
        whenever(getRandomFactUseCase.execute()).thenReturn(expectedFact)

        // Act
        viewModel = FactViewModel(getRandomFactUseCase)

        // Assert
        val actualFact = viewModel.fact.value
        assertEquals(expectedFact, actualFact)
    }
}