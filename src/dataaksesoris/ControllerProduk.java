package dataaksesoris;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;
import java.util.HashMap;

public class ControllerProduk {

    public void insertProduk(ProdukAksesoris produk) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(produk);
            transaction.commit();
            System.out.println("Produk berhasil disimpan ke database.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public List<ProdukAksesoris> getProdukList() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from ProdukAksesoris", ProdukAksesoris.class).list();
        }
    }

    public void cetakLaporan() {
        try {
            String laporanPath = "src/reports/LaporanProduk.jasper";
            HashMap<String, Object> parameter = new HashMap<>();
            JasperPrint jasperPrint = JasperFillManager.fillReport(laporanPath, parameter, HibernateUtil.getConnection());
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
            e.printStackTrace();
            System.out.println("Gagal mencetak laporan: " + e.getMessage());
        }
    }
}
