package com.sample;

import java.net.URL;
import java.time.chrono.Chronology;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

/**
 * Entry for localization information
 * 
 * @author tomo
 */
public class EntryStartController implements Initializable{
    @FXML
    private ComboBox<Locale> localeCombo;
    @FXML
    private VBox calendarVBox;
    private ToggleGroup calendarGroup = new ToggleGroup();

    @Override
    public void initialize(URL location, ResourceBundle resources) {        
        Locale.setDefault(Locale.ENGLISH);
        setLocaleCombo();
        setCalendar();
    }
    private void setLocaleCombo() {
        ObservableList<Locale> list = FXCollections.observableArrayList(
                new Locale("ja", "JP"),  // Japan
                new Locale("en", "US"),  // United States
                new Locale("fr", "FR"),  // France
                new Locale("de", "DE"),  // Germany
                new Locale("th", "TH"),  // Thailand
                new Locale("ar", "SA"),  // Saudi Arabia
                new Locale("zh", "TW")   // Taiwan
        );
        StringConverter<Locale> converter = new StringConverter<Locale>(){
            @Override
            public String toString(Locale object) {
                return String.format("%s(%s)", object.getDisplayCountry(), object.getDisplayLanguage());
            }
            @Override
            public Locale fromString(String string) {
                return null;
            }
        };
        localeCombo.getItems().addAll(list);
        localeCombo.setCellFactory(ComboBoxListCell.<Locale>forListView(converter));
        localeCombo.getSelectionModel().select(0);
    }

    private void setCalendar() {
        List<RadioButton> chronolories = Chronology.getAvailableChronologies().stream()
                .map(chronology -> {
                    RadioButton radioButton = new RadioButton(chronology.getDisplayName(TextStyle.FULL, Locale.getDefault()));
                    radioButton.setToggleGroup(calendarGroup);
                    radioButton.setUserData(chronology);
                    if (chronology.getCalendarType().equals("iso8601")) {
                        radioButton.setSelected(true);
                    }
                    return radioButton;
                }).collect(Collectors.toList());
        calendarVBox.getChildren().addAll(chronolories);
    }
    public LocalizationInfo getLocalizationInfo() {
        Locale locale = localeCombo.getSelectionModel().getSelectedItem();
        Chronology chronology = (Chronology) calendarGroup.getSelectedToggle().getUserData();
        LocalizationInfo localizationInfo = new LocalizationInfo();
        localizationInfo.setLocale(locale);
        localizationInfo.setChronolory(chronology);
        return localizationInfo;
    }
}
