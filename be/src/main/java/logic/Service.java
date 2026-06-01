package pogra4.be.logic;

import pogra4.be.data.*;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service

public class Service {
    @Autowired
    private EmpresasRepository empresas;
    @Autowired
    private AdminRepository admins;
    @Autowired
    private PuestoRepository puestos;
    @Autowired
    private CaracteristicaRepository carecteristica;
    @Autowired
    private OferenteRepository oferentes;
    @Autowired
    private pogra4.be.data.OferenteHasCaracteristicaRepository habilidadesRepo;
    public Iterable<Empresa> empresasAll() {
        return empresas.findAll();
    }
    public Iterable<Admin> adminsAll() { return admins.findAll();}
    public Iterable<Puesto> puestosAll() { return puestos.findAll();}
    public Iterable<Caracteristica> carecteristicasAll() { return carecteristica.findAll();}
    public Iterable<Oferente> oferentesAll() { return oferentes.findAll();}

    public void empresasAdd(Empresa empresa) {
        if(empresas.existsById(empresa.getId())) {
            throw new IllegalArgumentException("La empresa ya existe");
        }
        empresas.save(empresa);
    }
    public Empresa findEmpresaByCorreo(String correo) {
        return empresas.findByCorreo(correo);
    }
    public void adminsAdd(Admin admin) {
        if(admins.existsById(admin.getId())) {
            throw new IllegalArgumentException("El admin ya existe");
        }
        admins.save(admin);
    }
    public Admin findAdminById(String id) {
        return admins.findById(id).orElse(null);
    }
    public void puestosAdd(Puesto puesto) {
        if(puestos.existsById(puesto.getId())) {
            throw new IllegalArgumentException("El puesto ya existe");
        }
        puestos.save(puesto);
    }
    public void carecteristicasAdd(Caracteristica caracteristica) {
        if(carecteristica.existsById(caracteristica.getCaracteristicaId())) {
            throw new IllegalArgumentException("La caracteristica ya existe");
        }
        carecteristica.save(caracteristica);
    }
    public void oferentesAdd(Oferente oferente) {
        if(oferentes.existsById(oferente.getId())) {
            throw new IllegalArgumentException("El oferente ya existe");
        }
        oferentes.save(oferente);
    }
    public Oferente findOferenteByCorreo(String correo) {
        return oferentes.findByCorreo(correo);
    }

    public java.util.List<CandidatoDTO> buscarCandidatos(String puestoId) {
        // TODO: Implement candidate search logic. Return empty list for now to compile.
        return java.util.Collections.emptyList();
    }

    public Iterable<Puesto> puestosPorEmpresa(String empresaId) {
        java.util.List<Puesto> res = new java.util.ArrayList<>();
        for (Puesto p : puestos.findAll()) {
            Empresa e = p.getEmpresa();
            if (e != null && empresaId.equals(e.getId())) res.add(p);
        }
        return res;
    }

    public void crearPuesto(String empresaId, String descripcion, String salario, String tipo, java.util.List<String> caracteristicaIds, java.util.List<Integer> niveles) {
        Empresa e = empresas.findById(empresaId).orElseThrow(() -> new IllegalArgumentException("Empresa no encontrada"));
        Puesto p = new Puesto();
        p.setId(java.util.UUID.randomUUID().toString());
        p.setDescripcion(descripcion);
        p.setSalario(salario);
        p.setTipo(tipo);
        p.setEmpresa(e);
        p.setActivo((byte)1);
        p.setFechaRegistro(java.time.Instant.now());
        puestos.save(p);
    }

    public Puesto findPuestoById(String id) {
        return puestos.findById(id).orElse(null);
    }

    public void desactivarPuesto(String id) {
        Puesto p = puestos.findById(id).orElse(null);
        if (p != null) {
            p.setActivo((byte)0);
            puestos.save(p);
        }
    }

    public Oferente findOferenteById(String id) {
        return oferentes.findById(id).orElse(null);
    }

    public Iterable<OferenteHasCaracteristica> habilidadesDeOferente(String id) {
        return habilidadesRepo.findByOferenteId(id);
    }

    public void agregarHabilidad(String oferenteId, String caracteristicaId, int nivel) {
        Oferente o = oferentes.findById(oferenteId).orElseThrow(() -> new IllegalArgumentException("Oferente no encontrado"));
        Caracteristica c = carecteristica.findById(caracteristicaId).orElse(null);
        OferenteHasCaracteristica oh = new OferenteHasCaracteristica();
        oh.setId(java.util.UUID.randomUUID().toString());
        oh.setOferente(o);
        oh.setCaracteristicaCaracteristica(c);
        oh.setNivel(nivel);
        habilidadesRepo.save(oh);
    }

    public void eliminarHabilidad(String habilidadId) {
        habilidadesRepo.deleteById(habilidadId);
    }

    public void actualizarCurriculumOferente(String id, String filename) {
        Oferente o = oferentes.findById(id).orElse(null);
        if (o != null) {
            o.setCurriculum(filename);
            oferentes.save(o);
        }
    }

    public Iterable<Empresa> empresasPendientes() {
        java.util.List<Empresa> res = new java.util.ArrayList<>();
        for (Empresa e : empresas.findAll()) {
            if ("pendiente".equals(e.getEstado())) res.add(e);
        }
        return res;
    }

    public void aprobarEmpresa(String id) {
        Empresa e = empresas.findById(id).orElseThrow(() -> new IllegalArgumentException("Empresa no encontrada"));
        e.setEstado("aprobada");
        empresas.save(e);
    }

    public Iterable<Oferente> oferentesPendientes() {
        java.util.List<Oferente> res = new java.util.ArrayList<>();
        for (Oferente o : oferentes.findAll()) {
            if ("pendiente".equals(o.getEstado())) res.add(o);
        }
        return res;
    }

    public void aprobarOferente(String id) {
        Oferente o = oferentes.findById(id).orElseThrow(() -> new IllegalArgumentException("Oferente no encontrado"));
        o.setEstado("aprobado");
        oferentes.save(o);
    }

    public Iterable<Caracteristica> getRaices() {
        java.util.List<Caracteristica> res = new java.util.ArrayList<>();
        for (Caracteristica c : carecteristica.findAll()) {
            if (c.getPadre() == null) res.add(c);
        }
        return res;
    }

    public Iterable<Caracteristica> getHijos(String id) {
        java.util.List<Caracteristica> res = new java.util.ArrayList<>();
        for (Caracteristica c : carecteristica.findAll()) {
            if (c.getPadre() != null && id.equals(c.getPadre().getCaracteristicaId())) res.add(c);
        }
        return res;
    }

    public void crearCaracteristica(String nombre, String padreId) {
        Caracteristica c = new Caracteristica();
        c.setCaracteristicaId(java.util.UUID.randomUUID().toString());
        c.setNombre(nombre);
        c.setDescripcion(null);
        if (padreId != null && !padreId.isBlank()) {
            Caracteristica padre = carecteristica.findById(padreId).orElse(null);
            c.setPadre(padre);
        }
        carecteristica.save(c);
    }

}