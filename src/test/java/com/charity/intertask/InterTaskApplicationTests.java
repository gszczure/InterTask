package com.charity.intertask;

import com.charity.intertask.dto.FinancialReportDto;
import com.charity.intertask.dto.FundraisingEventDto;
import com.charity.intertask.dto.MoneyDto;
import com.charity.intertask.model.BoxMoney;
import com.charity.intertask.model.CollectionBox;
import com.charity.intertask.model.Currency;
import com.charity.intertask.model.FundraisingEvent;
import com.charity.intertask.repository.CollectionBoxRepository;
import com.charity.intertask.repository.FundraisingEventRepository;
import com.charity.intertask.service.ICurrencyExchangeService;
import com.charity.intertask.service.IFundraisingEventService;
import com.charity.intertask.service.impl.CollectionBoxService;
import com.charity.intertask.service.impl.FundraisingEventService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InterTaskApplicationTests {

    @Mock
    private CollectionBoxRepository boxRepository;

    @Mock
    private FundraisingEventRepository eventRepository;

    @Mock
    private IFundraisingEventService eventService;

    @Mock
    private ICurrencyExchangeService currencyExchangeService;

    @InjectMocks
    private CollectionBoxService boxService;

    @InjectMocks
    private FundraisingEventService fundraisingEventService;

    private CollectionBox emptyBox;
    private CollectionBox nonEmptyBox;
    private FundraisingEvent event;
    private FundraisingEventDto eventDto;

    @BeforeEach
    void setUp() {
        event = new FundraisingEvent.builder()
                .id(1L)
                .name("Test Event")
                .currency(Currency.EUR)
                .balance(BigDecimal.ZERO)
                .build();

        eventDto = new FundraisingEventDto();
        eventDto.setName("Test Event");
        eventDto.setCurrency(Currency.EUR);

        emptyBox = new CollectionBox();
        emptyBox.setId(1L);
        emptyBox.setMoneyContents(new ArrayList<>());

        nonEmptyBox = new CollectionBox();
        nonEmptyBox.setId(2L);
        nonEmptyBox.setMoneyContents(new ArrayList<>());
        BoxMoney money = new BoxMoney.builder()
                .id(1L)
                .collectionBox(nonEmptyBox)
                .currency(Currency.USD)
                .amount(new BigDecimal("10"))
                .build();
        nonEmptyBox.getMoneyContents().add(money);
    }

    @Test
    void registerBox_ShouldCreateNewBox() {
        when(boxRepository.save(any(CollectionBox.class))).thenAnswer(i -> {
            CollectionBox box = i.getArgument(0);
            box.setId(1L);
            return box;
        });

        CollectionBox result = boxService.registerBox();

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(boxRepository).save(any(CollectionBox.class));
    }

    @Test
    void createEvent_ShouldCreateNewEvent() {
        when(eventRepository.save(any(FundraisingEvent.class))).thenAnswer(i -> {
            FundraisingEvent savedEvent = i.getArgument(0);
            savedEvent.setId(1L);
            return savedEvent;
        });

        FundraisingEvent result = fundraisingEventService.createEvent(eventDto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Event", result.getName());
        assertEquals(Currency.EUR, result.getCurrency());
        assertEquals(BigDecimal.ZERO, result.getBalance());
        verify(eventRepository).save(any(FundraisingEvent.class));
    }

    @Test
    void getEventById_WhenEventExists_ShouldReturnEvent() {
        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));

        FundraisingEvent result = fundraisingEventService.getEventById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Event", result.getName());
    }

    @Test
    void getEventById_WhenEventDoesNotExist_ShouldThrowException() {
        when(eventRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> fundraisingEventService.getEventById(99L));
    }

    @Test
    void getBoxById_WhenBoxExists_ShouldReturnBox() {
        when(boxRepository.findById(1L)).thenReturn(Optional.of(emptyBox));

        CollectionBox result = boxService.getBoxById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void getBoxById_WhenBoxDoesNotExist_ShouldThrowException() {
        when(boxRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> boxService.getBoxById(99L));
    }

    @Test
    void assignBoxToEvent_WhenBoxIsEmpty_ShouldAssignBox() {
        when(boxRepository.findById(1L)).thenReturn(Optional.of(emptyBox));
        when(eventService.getEventById(1L)).thenReturn(event);
        when(boxRepository.save(any(CollectionBox.class))).thenReturn(emptyBox);

        CollectionBox result = boxService.assignBoxToEvent(1L, 1L);

        assertNotNull(result);
        assertEquals(event, result.getAssignedEvent());
    }

    @Test
    void assignBoxToEvent_WhenBoxIsNotEmpty_ShouldThrowException() {
        when(boxRepository.findById(2L)).thenReturn(Optional.of(nonEmptyBox));
        when(eventService.getEventById(1L)).thenReturn(event);

        assertThrows(IllegalStateException.class, () -> boxService.assignBoxToEvent(2L, 1L));
    }

    @Test
    void addMoneyToBox_ShouldAddMoney_WhenBoxIsAssignedAndEmpty() {
        emptyBox.setAssignedEvent(event);
        when(boxRepository.findById(1L)).thenReturn(Optional.of(emptyBox));
        when(boxRepository.save(any(CollectionBox.class))).thenReturn(emptyBox);

        MoneyDto moneyDto = new MoneyDto(Currency.USD, new BigDecimal("10"));

        boxService.addMoneyToBox(1L, moneyDto);

        assertEquals(1, emptyBox.getMoneyContents().size());
        assertEquals(Currency.USD, emptyBox.getMoneyContents().getFirst().getCurrency());
        assertEquals(new BigDecimal("10"), emptyBox.getMoneyContents().getFirst().getAmount());
        assertEquals(event, emptyBox.getAssignedEvent());
        verify(boxRepository).save(emptyBox);
    }

    @Test
    void addMoneyToBox_WhenBoxIsAssigned_WhenBoxContainsOtherCurrency_ShouldAddNewCurrency() {
        nonEmptyBox.setAssignedEvent(event);
        when(boxRepository.findById(2L)).thenReturn(Optional.of(nonEmptyBox));
        when(boxRepository.save(any(CollectionBox.class))).thenReturn(nonEmptyBox);

        MoneyDto newMoneyDto = new MoneyDto(Currency.EUR, new BigDecimal("5.59"));

        boxService.addMoneyToBox(2L, newMoneyDto);

        assertEquals(2, nonEmptyBox.getMoneyContents().size());

        Optional<BoxMoney> usdMoney = nonEmptyBox.getMoneyContents().stream()
                .filter(m -> m.getCurrency() == Currency.USD)
                .findFirst();
        assertTrue(usdMoney.isPresent());
        assertEquals(new BigDecimal("10"), usdMoney.get().getAmount());

        Optional<BoxMoney> eurMoney = nonEmptyBox.getMoneyContents().stream()
                .filter(m -> m.getCurrency() == Currency.EUR)
                .findFirst();
        assertTrue(eurMoney.isPresent());
        assertEquals(new BigDecimal("5.59"), eurMoney.get().getAmount());

        verify(boxRepository).save(nonEmptyBox);
    }

    @Test
    void addMoneyToBox_WhenBoxIsNotAssigned_ShouldThrowException() {
        when(boxRepository.findById(1L)).thenReturn(Optional.of(emptyBox));

        MoneyDto moneyDto = new MoneyDto(Currency.USD, new BigDecimal("10"));

        assertThrows(IllegalStateException.class, () -> boxService.addMoneyToBox(1L, moneyDto));
    }

    @Test
    void withdrawFromBox_WhenBoxIsAssigned_ShouldTransferMoney() {
        nonEmptyBox.setAssignedEvent(event);
        when(boxRepository.findById(2L)).thenReturn(Optional.of(nonEmptyBox));
        when(currencyExchangeService.convert(any(), any(), any())).thenReturn(new BigDecimal("10"));

        boxService.withdrawFromBox(2L);

        assertTrue(nonEmptyBox.getMoneyContents().isEmpty());
        assertEquals(new BigDecimal("10"), event.getBalance());
    }

    @Test
    void withdrawFromBox_WhenBoxIsNotAssigned_ShouldThrowException() {
        when(boxRepository.findById(2L)).thenReturn(Optional.of(nonEmptyBox));

        assertThrows(IllegalStateException.class, () -> boxService.withdrawFromBox(2L));
    }

    @Test
    void generateFinancialReport_WithMultipleEvents_ShouldReturnCorrectReport() {
        FundraisingEvent event1 = new FundraisingEvent.builder()
                .id(1L)
                .name("Test Charity")
                .currency(Currency.EUR)
                .balance(new BigDecimal("3891.23"))
                .build();

        FundraisingEvent event2 = new FundraisingEvent.builder()
                .id(2L)
                .name("Cancer Fight Test")
                .currency(Currency.GBP)
                .balance(new BigDecimal("100231.98"))
                .build();

        List<FundraisingEvent> events = Arrays.asList(event1, event2);
        when(eventRepository.findAll()).thenReturn(events);

        FinancialReportDto report = fundraisingEventService.generateFinancialReport();

        assertNotNull(report);
        assertEquals(2, report.getEvents().size());

        FinancialReportDto.EventReportEntry entry1 = report.getEvents().getFirst();
        assertEquals("Test Charity", entry1.getName());
        assertEquals(new BigDecimal("3891.23"), entry1.getAmount());
        assertEquals(Currency.EUR, entry1.getCurrency());

        FinancialReportDto.EventReportEntry entry2 = report.getEvents().get(1);
        assertEquals("Cancer Fight Test", entry2.getName());
        assertEquals(new BigDecimal("100231.98"), entry2.getAmount());
        assertEquals(Currency.GBP, entry2.getCurrency());
    }

    @Test
    void generateFinancialReport_WithNoEvents_ShouldReturnEmptyReport() {
        when(eventRepository.findAll()).thenReturn(new ArrayList<>());

        FinancialReportDto report = fundraisingEventService.generateFinancialReport();

        assertNotNull(report);
        assertTrue(report.getEvents().isEmpty());
    }
}