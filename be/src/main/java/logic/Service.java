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
    @Autowired
    private pogra4.be.data.PuestoHasCaracteristicaRepository puestoHasCaracteristicaRepo;
    public Iterable<Empresa> empresasAll() {
        return empresas.findAll();
    }
    public Iterable<Admin> adminsAll() { return admins.findAll();}
    public Iterable<Puesto> puestosAll() { return puestos.findAll();}
    public Iterable<Puesto> ultimosPublicos() {
        java.util.List<Puesto> all = new java.util.ArrayList<>();
        for (Puesto p : puestos.findAll()) {
            if (p.getActivo() != null && p.getActivo() == 1 && p.getTipo() != null && p.getTipo().equalsIgnoreCase("publico")) {
                all.add(p);
            }
        }
        all.sort((a,b) -> b.getFechaRegistro().compareTo(a.getFechaRegistro()));
        java.util.List<Puesto> res = new java.util.ArrayList<>();
        for (int i = 0; i < Math.min(5, all.size()); i++) res.add(all.get(i));
        return res;
    }
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
        Puesto p = puestos.findById(puestoId).orElse(null);
        if (p == null) return java.util.Collections.emptyList();
        java.util.List<PuestoHasCaracteristica> reqs = puestoHasCaracteristicaRepo.findByPuestoId(puestoId);
        if (reqs == null || reqs.isEmpty()) return java.util.Collections.emptyList();
        int total = reqs.size();
        java.util.Map<String,Integer> reqMap = new java.util.HashMap<>();
        for (PuestoHasCaracteristica phc : reqs) {
            if (phc.getCaracteristicaCaracteristica()!=null)
                reqMap.put(phc.getCaracteristicaCaracteristica().getCaracteristicaId(), phc.getNivel() == null ? 1 : phc.getNivel());
        }
        java.util.List<CandidatoDTO> res = new java.util.ArrayList<>();
        for (Oferente o : oferentes.findAll()) {
            if (o.getEstado() == null || !o.getEstado().equalsIgnoreCase("aprobado")) continue;
            java.util.List<OferenteHasCaracteristica> hov = habilidadesRepo.findByOferenteId(o.getId());
            java.util.Map<String,Integer> have = new java.util.HashMap<>();
            for (OferenteHasCaracteristica oh : hov) {
                if (oh.getCaracteristicaCaracteristica()!=null)
                    have.put(oh.getCaracteristicaCaracteristica().getCaracteristicaId(), oh.getNivel()==null?1:oh.getNivel());
            }
            int matched = 0;
            for (java.util.Map.Entry<String,Integer> e : reqMap.entrySet()) {
                Integer he = have.get(e.getKey());
                if (he != null && he >= e.getValue()) matched++;
            }
            int porcentaje = (int) Math.round(100.0 * matched / total);
            res.add(new CandidatoDTO(o.getId(), o.getNombre(), o.getPrimerApellido(), porcentaje));
        }
        res.sort((a,b) -> Integer.compare(b.porcentajeCoincidencia(), a.porcentajeCoincidencia()));
        return res;
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
        if (caracteristicaIds != null) {
            for (int i = 0; i < caracteristicaIds.size(); i++) {
                String cid = caracteristicaIds.get(i);
                Integer niv = (niveles != null && niveles.size() > i) ? niveles.get(i) : 1;
                Caracteristica c = carecteristica.findById(cid).orElse(null);
                PuestoHasCaracteristica phc = new PuestoHasCaracteristica();
                PuestoHasCaracteristicaId id = new PuestoHasCaracteristicaId();
                id.setPuestoId(p.getId());
                id.setCaracteristicaCaracteristicaId(cid);
                phc.setId(id);
                phc.setPuesto(p);
                phc.setCaracteristicaCaracteristica(c);
                phc.setNivel(niv);
                puestoHasCaracteristicaRepo.save(phc);
            }
        }
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