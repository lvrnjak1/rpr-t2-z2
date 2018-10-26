package ba.unsa.etf.rpr.tutorijal02;

import java.util.Objects;

public class Interval {
    private double donjaGranicaIntervala;
    private double gornjaGranicaIntervala;
    private boolean ukljucenaDonjaGranicaIntervala;
    private boolean ukljucenaGornjaGranicaIntervala;


    public double getDonjaGranicaIntervala() {
        return donjaGranicaIntervala;
    }

    public void setDonjaGranicaIntervala(double donjaGranicaIntervala) {
        this.donjaGranicaIntervala = donjaGranicaIntervala;
    }

    public double getGornjaGranicaIntervala() {
        return gornjaGranicaIntervala;
    }

    public void setGornjaGranicaIntervala(double gornjaGranicaIntervala) {
        this.gornjaGranicaIntervala = gornjaGranicaIntervala;
    }

    public boolean isUkljucenaDonjaGranicaIntervala() {
        return ukljucenaDonjaGranicaIntervala;
    }

    public void setUkljucenaDonjaGranicaIntervala(boolean ukljucenaDonjaGranicaIntervala) {
        this.ukljucenaDonjaGranicaIntervala = ukljucenaDonjaGranicaIntervala;
    }

    public boolean isUkljucenaGornjaGranicaIntervala() {
        return ukljucenaGornjaGranicaIntervala;
    }

    public void setUkljucenaGornjaGranicaIntervala(boolean ukljucenaGornjaGranicaIntervala) {
        this.ukljucenaGornjaGranicaIntervala = ukljucenaGornjaGranicaIntervala;
    }

    public Interval(double donjaGranicaIntervala, double gornjaGranicaIntervala, boolean ukljucenaDonjaGranicaIntervala, boolean jeUkljucenaGornjaGranicaIntervala)
    throws  IllegalArgumentException {
        if(donjaGranicaIntervala > gornjaGranicaIntervala)
            throw new IllegalArgumentException("Donja granica je veca od gornje");
        setDonjaGranicaIntervala(donjaGranicaIntervala);
        setGornjaGranicaIntervala(gornjaGranicaIntervala);
        setUkljucenaDonjaGranicaIntervala(ukljucenaDonjaGranicaIntervala);
        setUkljucenaGornjaGranicaIntervala(jeUkljucenaGornjaGranicaIntervala);
    }

    public  Interval() {
        setDonjaGranicaIntervala(0);
        setGornjaGranicaIntervala(0);
        setUkljucenaDonjaGranicaIntervala(false);
        setUkljucenaGornjaGranicaIntervala(false);
    }

    public boolean isNull(){
        return getDonjaGranicaIntervala() == 0 && getGornjaGranicaIntervala() == 0 && getDonjaGranicaIntervala() == 0 && getGornjaGranicaIntervala() == 0;
    }

    public boolean isIn(double tackaKojuProvjeravamo){
        return ((tackaKojuProvjeravamo == getDonjaGranicaIntervala() && isUkljucenaDonjaGranicaIntervala()) ||
                (tackaKojuProvjeravamo == getDonjaGranicaIntervala() && isUkljucenaGornjaGranicaIntervala()) ||
                (getDonjaGranicaIntervala() < tackaKojuProvjeravamo && tackaKojuProvjeravamo < getGornjaGranicaIntervala()));
    }

    public Interval intersect(Interval drugiInterval){
        //nemaju presjeka
        if((getGornjaGranicaIntervala() < drugiInterval.getDonjaGranicaIntervala() && getDonjaGranicaIntervala() < drugiInterval.getGornjaGranicaIntervala()) ||
                (drugiInterval.getGornjaGranicaIntervala() < getDonjaGranicaIntervala() && drugiInterval.getDonjaGranicaIntervala() < drugiInterval.getGornjaGranicaIntervala())
        ) return  new Interval();

        //dodiruju se (ako su obje ukljucene vratit cu interval od jedne tacke)
        if(getGornjaGranicaIntervala() == drugiInterval.getGornjaGranicaIntervala() && isUkljucenaGornjaGranicaIntervala() && drugiInterval.isUkljucenaDonjaGranicaIntervala())
            return  new Interval(getDonjaGranicaIntervala(), getDonjaGranicaIntervala(), true, true);
        if(drugiInterval.getGornjaGranicaIntervala() == getGornjaGranicaIntervala() && drugiInterval.isUkljucenaGornjaGranicaIntervala() && isUkljucenaDonjaGranicaIntervala())
            return  new Interval(getDonjaGranicaIntervala(), getDonjaGranicaIntervala(), true, true);



        //preklapaju se
        if(getDonjaGranicaIntervala() == drugiInterval.getDonjaGranicaIntervala() && getGornjaGranicaIntervala() == drugiInterval.getGornjaGranicaIntervala())
            return new Interval(getDonjaGranicaIntervala(), getGornjaGranicaIntervala(), isUkljucenaDonjaGranicaIntervala() && drugiInterval.isUkljucenaDonjaGranicaIntervala(),
                    isUkljucenaGornjaGranicaIntervala() && drugiInterval.isUkljucenaDonjaGranicaIntervala());

        //obuhvataju se
        if(isIn(drugiInterval.getDonjaGranicaIntervala()) && isIn(drugiInterval.getGornjaGranicaIntervala()))
            return drugiInterval;

        if(drugiInterval.isIn(getDonjaGranicaIntervala()) && drugiInterval.isIn(getGornjaGranicaIntervala()))
            return this;

        //presjecaju se
        if(isIn(drugiInterval.getDonjaGranicaIntervala()) && !isIn(drugiInterval.getGornjaGranicaIntervala()))
            return new Interval(drugiInterval.getDonjaGranicaIntervala(), getGornjaGranicaIntervala(), drugiInterval.isUkljucenaDonjaGranicaIntervala(), isUkljucenaGornjaGranicaIntervala());
        if(drugiInterval.isIn(getDonjaGranicaIntervala()) && !drugiInterval.isIn(getGornjaGranicaIntervala()))
            return new Interval(getDonjaGranicaIntervala(), drugiInterval.getGornjaGranicaIntervala(), isUkljucenaDonjaGranicaIntervala(), drugiInterval.isUkljucenaGornjaGranicaIntervala());

        return new Interval();
    }

    public  static Interval intersect(Interval prviInterval, Interval drugiInterval){
        return prviInterval.intersect(drugiInterval);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Interval interval = (Interval) o;
        return Double.compare(interval.donjaGranicaIntervala, donjaGranicaIntervala) == 0 &&
                Double.compare(interval.gornjaGranicaIntervala, gornjaGranicaIntervala) == 0 &&
                ukljucenaDonjaGranicaIntervala == interval.ukljucenaDonjaGranicaIntervala &&
                ukljucenaGornjaGranicaIntervala == interval.ukljucenaGornjaGranicaIntervala;
    }

    @Override
    public int hashCode() {
        return Objects.hash(donjaGranicaIntervala, gornjaGranicaIntervala, ukljucenaDonjaGranicaIntervala, ukljucenaGornjaGranicaIntervala);
    }

    @Override
    public String toString() {
        if(isNull()) return "()";
        String ispis = new String();
        if(isUkljucenaDonjaGranicaIntervala()) ispis += "[";
        else ispis += "(";

        ispis += getDonjaGranicaIntervala() + "," + getGornjaGranicaIntervala();

        if(isUkljucenaGornjaGranicaIntervala()) ispis += "]";
        else ispis += ")";

        return ispis;
    }
}
