package com.root.drivinghistory.component;

import com.root.drivinghistory.repository.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
public class DriverCommandParserTest {

    @Mock
    DriverRepository driverRepository;

    @InjectMocks
    DriverCommandParser driverCommandParser;

    @Test
    public void null_line_is_invalid () {
        driverCommandParser.parseAndSaveDriverName(null);

        verifyNoInteractions(driverRepository);
    }


    @Test
    public void empty_line_is_invalid () {
        driverCommandParser.parseAndSaveDriverName("");

        verifyNoInteractions(driverRepository);
    }

    @Test
    public void blank_line_is_invalid () {
        driverCommandParser.parseAndSaveDriverName("    ");

        verifyNoInteractions(driverRepository);
    }

    @Test
    public void line_must_start_with_Driver () {
        driverCommandParser.parseAndSaveDriverName("Add Dan");
        driverCommandParser.parseAndSaveDriverName("Add Driver Dan ");
        driverCommandParser.parseAndSaveDriverName("DDriver Dan ");

        verifyNoInteractions(driverRepository);
    }

    @Test
    public void driver_without_name_is_invalid () {
        driverCommandParser.parseAndSaveDriverName("Driver");
        driverCommandParser.parseAndSaveDriverName("Driver   ");

        verifyNoInteractions(driverRepository);
    }

    @Test
    public void driver_with_name_is_valid () {
        driverCommandParser.parseAndSaveDriverName("Driver Dan");
        driverCommandParser.parseAndSaveDriverName("Driver Donkey Kong");

        verify(driverRepository).save("Dan");
        verify(driverRepository).save("Donkey Kong");
        verifyNoMoreInteractions(driverRepository);

    }

    @Test
    public void validator_ignores_extra_spaces_in_between_words () {
        driverCommandParser.parseAndSaveDriverName("Driver    Donkey   Kong");

        verify(driverRepository).save("Donkey Kong");
        verifyNoMoreInteractions(driverRepository);
    }

    @Test
    public void driver_can_have_name_driver () {
        driverCommandParser.parseAndSaveDriverName("Driver Driver");
        driverCommandParser.parseAndSaveDriverName("Driver Is Driver Donkey Kong");

        verify(driverRepository).save("Driver");
        verify(driverRepository).save("Is Driver Donkey Kong");
        verifyNoMoreInteractions(driverRepository);
    }

}
