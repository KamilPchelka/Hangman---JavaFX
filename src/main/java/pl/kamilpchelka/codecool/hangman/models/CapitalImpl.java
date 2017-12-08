package pl.kamilpchelka.codecool.hangman.models;

public class CapitalImpl implements Capital {

    private String capitalName;
    private String countryName;

    @Override
    public String getCapitalName() {

        return capitalName;
    }

    @Override
    public void setCapitalName(String capitalName) {

        this.capitalName = capitalName;
    }

    @Override
    public String getCountryName() {

        return countryName;
    }

    @Override
    public void setCountryName(String countryName) {

        this.countryName = countryName;
    }
}
