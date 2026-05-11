# App Clínica Maya Yamamoto RPG — Entrega 1

App Android (Java) desenvolvido para a clínica de fisioterapia
da Maya Yoshiko Yamamoto. Projeto interdisciplinar do 3º semestre
de ADS — disciplina de Programação Mobile.

---

## Como instalar os arquivos no Android Studio

1. Abra o seu projeto no Android Studio (com pacote `com.example.pi_maya`).
2. **Imagens** — coloque em `app/src/main/res/drawable/`:
   - `maya.png` (foto da Maya)
   - `mayaimagemlogo1.png` (logo)
   - `exercicio_coluna.png`, `exercicio_cervical.png`, `exercicio_lombar.png`
   - `exemplo_exercicio.png` (Maya atendendo na maca — banner principal)
   - `sala.png` (foto do consultório)
3. **Arquivos da pasta `res/`** — substitua os arquivos correspondentes:
   - `res/values/colors.xml`, `res/values/strings.xml`
   - `res/drawable/*.xml` (todos os arquivos de drawable)
   - `res/layout/*.xml` (todos os layouts)
4. **Arquivos Java** — copie todos para
   `app/src/main/java/com/example/pi_maya/`.
5. **AndroidManifest.xml** — copie o conteúdo de `AndroidManifest_trecho.xml`
   para o seu manifest, dentro da tag `<application>`. Mantenha o
   `android:icon`, `android:label` e `android:theme` que já estavam.
6. **Sync** o projeto e rode (Run ▶).

---

## Credenciais de teste

O `BancoDeDados.java` já vem com 5 usuários pré-cadastrados pra
facilitar a apresentação:

| Tipo     | E-mail              | Senha       | Quem é                        |
|----------|---------------------|-------------|-------------------------------|
| Admin    | admin@gmail.com     | admin123    | Maya Yoshiko Yamamoto         |
| Paciente | usuario@gmail.com   | usuario123  | Maria Silva (paciente padrão) |
| Paciente | joao@gmail.com      | joao123     | João Pereira                  |
| Paciente | ana@gmail.com       | ana123      | Ana Costa                     |
| Paciente | carlos@gmail.com    | carlos123   | Carlos Oliveira               |

A Maria Silva já tem 2 consultas pré-agendadas pra mostrar na tela
"Minhas Consultas".

---

## Estrutura de telas (mapa do app)

```
                   ┌──────────────┐
                   │ MainActivity │  (home pública — banner + sobre + menu)
                   └──────┬───────┘
                          │
       ┌──────────┬───────┼────────┬──────────┬──────────┐
       ▼          ▼       ▼        ▼          ▼          ▼
   Agendar   Exercícios Preços Contato      Login    (back)
       │                                       │
       │                                       ▼
       │                              ┌────────────────┐
       │                              │ LoginActivity  │
       │                              └───────┬────────┘
       │                                      │
       │                          ┌───────────┴────────────┐
       │                          ▼                        ▼
       │                  ┌──────────────┐         ┌──────────────────┐
       │                  │ AdminActivity│         │ UsuarioActivity  │
       │                  └──────┬───────┘         └────────┬─────────┘
       │                         │                          │
       │      ┌──────────┬───────┼─────────────┐    ┌───────┼────────┐
       │      ▼          ▼       ▼             ▼    ▼       ▼        ▼
       │  Cadastro   Agendar  Consultas Aniversário Agendar Consultas Perfil
       │  Usuario    Consulta Agendadas
       └─────────────► (mesma tela, modos diferentes)
```

---

## Arquivos do projeto

### Modelos (POJOs simples)
- **Usuario.java** — paciente ou admin (flag `ehAdmin`).
- **Consulta.java** — guarda CPF, data e hora.
- **Exercicio.java** — exercícios prescritos.

### "Banco de Dados" simulado
- **BancoDeDados.java** — singleton estático com `ArrayList`s.
  Ainda não usamos SQLite porque é conteúdo da Entrega 2; aqui
  usamos memória mesmo. Tem métodos pra fazer login, cadastrar,
  buscar por CPF, agendar consulta e listar aniversariantes
  (semana, mês ou ano todo).

### Activities

| Activity                       | O que faz                                            |
|--------------------------------|------------------------------------------------------|
| `MainActivity`                 | Tela inicial pública com banner, "sobre" e menu.     |
| `ExerciciosActivity`           | Casco que carrega o `ExerciciosFragment`.            |
| `PrecosActivity`               | Lista das 3 modalidades com valores.                 |
| `ContatoActivity`              | Endereço, telefone, e-mail e botão pro Google Maps.  |
| `LoginActivity`                | Validação contra o BancoDeDados; redireciona.        |
| `AdminActivity`                | Painel da Maya — cadastrar, agendar, consultas, aniversariantes. |
| `UsuarioActivity`              | Painel do paciente — agendar, minhas consultas, perfil. |
| `CadastroUsuarioActivity`      | Formulário completo + foto da galeria.               |
| `AgendarConsultaActivity`      | Agendamento (modos admin/paciente) com Date/Time pickers. |
| `MinhasConsultasActivity`      | Lista de consultas (paciente vê só as suas; admin vê todas). |
| `AniversariantesActivity`      | Filtros semana/mês/ano com listagem dinâmica.        |
| `PerfilActivity`               | Mostra os dados completos do usuário logado.         |

### Fragments
- **SobreFragment** — usado dentro da MainActivity, mostra a
  apresentação da Maya, foto da sala e o "O que é RPG?".
- **ExerciciosFragment** — usado dentro da ExerciciosActivity,
  mostra os 3 exercícios prescritos com botão "marcar como feito".

---

## Decisões técnicas (e por quê)

- **Sem RecyclerView**: as listas dinâmicas (consultas e
  aniversariantes) são montadas com `LinearLayout` vertical e
  `addView()` — isso é o que conseguimos fazer com o que vimos
  até o 3º semestre.
- **Sem SQLite ainda**: usei `ArrayList` estático na classe
  `BancoDeDados`. Os dados se perdem ao fechar o app — é
  intencional pra Entrega 1.
- **Foto da galeria**: usei `ActivityResultLauncher` em vez do
  `onActivityResult` antigo. É o jeito recomendado pela
  documentação atual do Android.
- **Datas**: guardadas como `String` no formato `dd/MM/yyyy`
  pra simplificar. Pra escolher, usamos `DatePickerDialog` e
  `TimePickerDialog`, que já vêm no Android.
- **Modo admin/paciente na mesma tela**: a `AgendarConsultaActivity`
  e a `MinhasConsultasActivity` funcionam pros dois — a Activity
  decide o que mostrar baseada em quem está logado. Isso evita
  duplicar código.
- **Cores pastel**: troquei os tons fortes por versões mais
  suaves no `colors.xml`. Mantive os nomes das cores antigos
  (`azulPetroleo`, `coral`, etc.) pra não precisar mexer em
  todos os layouts.
- **Variáveis em português**: como combinado em sala — fica
  mais fácil de entender o código pra quem está aprendendo.

---

## Requisitos da Entrega 1 (cumpridos)

- ✅ Múltiplas Activities (12 no total)
- ✅ Intents explícitos (entre as Activities)
- ✅ Intents implícitos (Contato: ligar, e-mail, WhatsApp, mapa)
- ✅ Fragments (SobreFragment, ExerciciosFragment)
- ✅ ConstraintLayout (telas principais)
- ✅ Dados numéricos (preços, contadores de exercícios, totais)
- ✅ ScrollView (rolagem nas telas longas)
- ✅ Validação de formulários e Toasts informativos

## Próxima entrega (planejado)

- Substituir `BancoDeDados` por SQLite ou consumo de API REST.
- Listas com `RecyclerView` em vez de `addView()`.
- Notificações push pra lembrete de consulta.
