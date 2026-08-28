# JAVA_HOME apontando pro JRE errado

## Sintoma
`mvnw.cmd` falhava com:
```
No compiler is provided in this environment. Perhaps you are running on a JRE rather than a JDK?
```
Mas rodando pelo botão Run do VS Code, o projeto compilava e chegava até erros de runtime (Flyway/DataSource).

## Investigação
- Terminal (`java -version`) mostrava Java 8 — só JRE, sem `javac`.
- O projeto pede Java 21 (`pom.xml`).
- Se o VS Code conseguia compilar, ele tinha um JDK 21 em algum lugar que o terminal não enxergava.
- Busquei um JDK 21 completo no sistema e achei um **embutido na extensão Java do VS Code**:
  ```
  C:\Users\marco\.vscode\extensions\redhat.java-1.55.0-win32-x64\jre\21.0.11-win32-x86_64
  ```

## Causa raiz
O terminal usava o Java do PATH do sistema (JRE 8, sem compilador). O VS Code usava um JDK 21 próprio, baixado pela extensão `redhat.java`, que nunca foi exposto como `JAVA_HOME` do sistema.

## Solução (temporária, por sessão de terminal)
```cmd
set JAVA_HOME=C:\Users\marco\.vscode\extensions\redhat.java-1.55.0-win32-x64\jre\21.0.11-win32-x86_64
set PATH=%JAVA_HOME%\bin;%PATH%
```

## Solução definitiva (pendente)
Instalar um JDK 21 de verdade (ex: Eclipse Temurin) e configurar `JAVA_HOME` permanentemente nas variáveis de ambiente do Windows — depender do JDK escondido numa extensão é frágil (quebra se a extensão atualizar).

## Lição
Dois "Javas" diferentes coexistindo no mesmo Windows (um pro terminal, outro pro VS Code) é uma causa clássica de "funciona na minha IDE mas não no terminal" — vale sempre verificar `JAVA_HOME` quando o comportamento diverge entre os dois.
