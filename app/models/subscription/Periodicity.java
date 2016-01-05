package models.subscription;

import play.i18n.Messages;

public enum Periodicity {

    UNIQUE(0),
    MONTH(1),
    BIMONTH(2),
    TRIMESTER(3),
    SEMESTER(5),
    YEAR(12);

    private int value;

    Periodicity(int value) {
        this.value = value;
    }

    public long getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        switch (this) {
            case UNIQUE:
                return Messages.get("model.periodicity.unique.title");
            case MONTH:
                return Messages.get("model.periodicity.month.title");
            case BIMONTH:
                return Messages.get("model.periodicity.bimonth.title");
            case TRIMESTER:
                return Messages.get("model.periodicity.trimester.title");
            case SEMESTER:
                return Messages.get("model.periodicity.semester.title");
            case YEAR:
                return Messages.get("model.periodicity.year.title");
            default:
                return null;
        }
    }
}
