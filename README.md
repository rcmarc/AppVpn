# AppVpn
 
## Instalación

1. Instalar libpcap
    * Windows: https://www.winpcap.org/install/bin/WinPcap_4_1_3.exe
    * Linux: Seguir las instrucciones de: http://www.linuxfromscratch.org/blfs/view/svn/basicnet/libpcap.html
1. Añadir la carpeta JnetPcap a la variable de entorno del sistema
1. Instalar PostgreSQL
    * Crear una base de datos vacía con el nombre vpn_events

## Ejecución

Se requiere tener instalado maven

ejecutar:
```
    mvn package
```

luego:
```
    java -jar target\appvpn-0.0.1
```