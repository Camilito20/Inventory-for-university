# Programa de inventario

Este proyecto es una aplicación de gestión de inventario básico.  
En esta primera versión no incluye herramientas de análisis de productos.

El proyecto trabaja con:
- SQLite como base de datos
- Archivos Excel para que el usuario lleve un historial diario del inventario
- Una interfaz gráfica (GUI) desarrollada con JavaFX

---

### Estructura (Primera parte)

- **Main**: pruebas iniciales en consola
- **Product**
  - **Ingredient**: contiene los datos de los ingredientes
  - **Product**: contiene los datos del producto
  - **ManagerProducts**: contiene la lógica para manejar los productos e ingredientes

---

### Descripción

En esta primera parte del proyecto se implementa la estructura base que permite:
agregar, eliminar, editar, vender y recargar productos e ingredientes.  
En esta etapa, los productos aún no incluyen ingredientes.
