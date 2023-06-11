package modelo;

public class UsuarioCache {
    
    public static Usuario usuarioCache = new Usuario();

    public static Usuario getUsuarioCache() {
        return usuarioCache;
    }

    public static void setUsuarioCache(Usuario usuarioCache) {
        UsuarioCache.usuarioCache = usuarioCache;
    }

    
}
