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

    public boolean tangent(Interval drugiInterval){
        //funkcija koja provjerava da li je gornja prvog jednaka gornjoj drugog i da li su obe ukljucene
        return getGornjaGranicaIntervala() == drugiInterval.getDonjaGranicaIntervala() &&
                isUkljucenaGornjaGranicaIntervala() && drugiInterval.isUkljucenaDonjaGranicaIntervala();
    }

    public Interval intersect(Interval drugiInterval){

        //napraviti da je i1 interval koji ima manju donju granicu (prvi je na brojnoj liniji)
        Interval i1 = new Interval(getDonjaGranicaIntervala(), getGornjaGranicaIntervala(), isUkljucenaDonjaGranicaIntervala(),
                isUkljucenaGornjaGranicaIntervala());
        Interval i2 = new Interval(drugiInterval.getDonjaGranicaIntervala(), drugiInterval.getGornjaGranicaIntervala(),
                drugiInterval.isUkljucenaDonjaGranicaIntervala(), drugiInterval.isUkljucenaGornjaGranicaIntervala());

        if(i2.getDonjaGranicaIntervala() < i1.getDonjaGranicaIntervala()){
            swapIntervals(i1, i2);
        }

        //ne presjecaju se
        if(i2.getDonjaGranicaIntervala() > i1.getGornjaGranicaIntervala() || ( i2.getDonjaGranicaIntervala() == i1.getGornjaGranicaIntervala() && !i1.tangent(i2))){
            return new Interval();
        }

        //presjecaju se
        double donja = i2.getDonjaGranicaIntervala();
        boolean ukljucena_donja = i2.isUkljucenaDonjaGranicaIntervala();
        if(i1.getDonjaGranicaIntervala() == i2.getDonjaGranicaIntervala()){
            ukljucena_donja = i1.isUkljucenaDonjaGranicaIntervala() && i2.isUkljucenaDonjaGranicaIntervala();
        }

        double gornja = i1.getGornjaGranicaIntervala();
        boolean ukljucena_gornja = i1.isUkljucenaGornjaGranicaIntervala();
        if(i1.getGornjaGranicaIntervala() > i2.getGornjaGranicaIntervala()){
            gornja = i2.getGornjaGranicaIntervala();
            ukljucena_gornja = i2.isUkljucenaGornjaGranicaIntervala();
        }else if(i1.getGornjaGranicaIntervala() == i2.getGornjaGranicaIntervala()){
            ukljucena_gornja = i1.isUkljucenaGornjaGranicaIntervala() && i2.isUkljucenaGornjaGranicaIntervala();
        }

        return new Interval(donja, gornja, ukljucena_donja, ukljucena_gornja);
    }

    private void swapIntervals(Interval i1, Interval i2) {
        double tmp = i1.getDonjaGranicaIntervala();
        i1.setDonjaGranicaIntervala(i2.getDonjaGranicaIntervala());
        i2.setDonjaGranicaIntervala(tmp);

        tmp = i1.getGornjaGranicaIntervala();
        i1.setGornjaGranicaIntervala(i2.getGornjaGranicaIntervala());
        i2.setGornjaGranicaIntervala(tmp);

        boolean tmp_ukljuceno = i1.isUkljucenaDonjaGranicaIntervala();
        i1.setUkljucenaDonjaGranicaIntervala(i2.isUkljucenaDonjaGranicaIntervala());
        i2.setUkljucenaDonjaGranicaIntervala(tmp_ukljuceno);

        tmp_ukljuceno = i1.isUkljucenaGornjaGranicaIntervala();
        i1.setUkljucenaGornjaGranicaIntervala(i2.isUkljucenaGornjaGranicaIntervala());
        i2.setUkljucenaGornjaGranicaIntervala(tmp_ukljuceno);
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
        String ispis = "";
        if(isUkljucenaDonjaGranicaIntervala()) ispis += "[";
        else ispis += "(";

        ispis += getDonjaGranicaIntervala() + "," + getGornjaGranicaIntervala();

        if(isUkljucenaGornjaGranicaIntervala()) ispis += "]";
        else ispis += ")";

        return ispis;
    }
}
