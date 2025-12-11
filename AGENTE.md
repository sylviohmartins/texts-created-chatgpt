# AGENTS.md

> **Starter React (TypeScript) — React 19 + Vite 7 + Tailwind CSS 4 + TanStack + RHF + i18n**
>
> Este arquivo orienta _agentes de código_ e colaboradores automatizados a trabalhar neste projeto
> com segurança e consistência. Siga estes comandos, regras e receitas antes de alterar arquivos.

### Setup commands

- Instalação das dependências: `pnpm install`
- Ambiente de desenvolvimento: `pnpm dev`
- Build de produção: `pnpm build`
- Pré-visualização do build: `pnpm preview`
- Lint (ESLint): `pnpm lint`
- Formatação (Prettier): `pnpm format`
- Checagem de tipos (TypeScript): `pnpm typecheck` _(equivalente a `tsc --noEmit`; ajuste conforme
  `package.json`)_
- Testes e e2e (Cypress em modo headless): `pnpm test:e2e`
- Testes e e2e (Cypress interativo): `pnpm cy:open`

> Observação: o padrão recomendado é usar **pnpm** como gerenciador de pacotes (com
> `pnpm-lock.yaml`). Não use `npm` ou `yarn`.

### Stack & libs

- **Core**: React 19+, TypeScript 5+, Vite 7+, Tailwind CSS 4+ (`@tailwindcss/vite`), WindiCSS (via
  `vite-plugin-windicss`), class-variance-authority, clsx, tailwind-merge, motion (Framer Motion
  rebrand).
- **Roteamento**: react-router 7 (`react-router-dom`).
- **Estado e dados**: @tanstack/react-query 5 (+ Devtools), ky (HTTP), qs (querystring), zod 4
  (schemas/validação).
- **Formulários**: react-hook-form 7 + @hookform/resolvers (padrão).
- **Tabela**: @tanstack/react-table 8.
- **i18n**: i18next + react-i18next (+ browser-languagedetector + http-backend). `react-intl` é
  **complementar** (formatação), não base.
- **UI / A11y**: Radix UI (incl. `@radix-ui/react-slot`), react-aria-components, lucide-react
  (ícones), cmdk (Command Menu), vaul (drawers), input-otp, notistack e sonner (toasts).
- **Gráficos**: Recharts 2 (padrão) e ApexCharts 4 (casos específicos avançados).
- **Mapas**: Leaflet + react-leaflet 5.
- **D&D**: @dnd-kit (core/modifiers/sortable).
- **Outros**: date-fns 4, react-top-loading-bar, react-inlinesvg, react-resizable-panels,
  mini-svg-data-uri, next-themes (tema), react-helmet(-async).

> **Preferências oficiais** neste tipo de projeto: **RHF + Zod** (forms), **React Query + ky**
> (dados), **i18next** (i18n), **Recharts** (gráficos).

### Estrutura de pastas (onde colocar o quê)

```txt
cypress/             # Suíte E2E (ver cypress.config.ts)
  e2e/               # Specs organizadas por domínio/página
  fixtures/          # Mock data usados pelos testes
  support/           # Commands, tasks e bootstrap do Cypress
mock-api/            # JSON Server + rotas auxiliares para simular backend
  db/                # Bases JSON (jobs.json, job-runs.json etc.)
  routes/            # Rotas/handlers customizados (middlewares, seeds)
public/              # Assets estáticos servidos pelo Vite
src/
  components/        # Componentes headless/visuais reutilizáveis (stateless; sem acoplamento de negócio)
  config/            # Configs estáticas (i18n, tema, axios/ky, dayjs/date-fns, etc.) sem side effects globais
  features/          # Feature-based (subpastas por domínio/subárea). Ex.: account/appearance/{components,hooks,pages,schemas,services,types,utils}
  hooks/             # React hooks genéricos (de infra/UI), não específicos de uma feature
  layouts/           # Layouts de páginas (toolbar, sidebars, shells reutilizáveis)
  lib/               # Adapters/clients (HTTP com ky), factories, helpers de infraestrutura
  pages/             # Páginas de alto nível (quando aplicável ao roteamento)
  providers/         # Providers globais (Theme, QueryClient, I18n, Router, Toast)
  routing/           # Definições de rotas, `lazy()` e loaders/actions
  styles/            # CSS global, tokens e diretivas do Tailwind
  types/             # Tipos globais utilitários (TS)
  utils/             # Funções puras utilitárias globais (sem I/O), nomeadas *.util.ts
  App.tsx
  main.tsx
  vite-env.d.ts
.editorconfig        # Convenções de indentação/whitespace compartilhadas entre IDEs.
.env                 # Variáveis de ambiente (não comitar valores sensíveis).
.gitignore           # Exclusões do Git.
.prettierignore      # Arquivos ignorados pelo Prettier.
components.json      # Mapeamento consumido por Storybook/VSCode para ReUI.
cypress.config.ts    # Configuração principal do Cypress.
eslint.config.mjs    # Regras do ESLint.
index.html           # Entry HTML servida pelo Vite.
package.json         # Manifest do projeto (scripts, dependências).
pnpm-lock.yaml       # Lockfile do pnpm.
prettier.config.cjs  # Formato/lint automatizado.
README.md            # Visão geral do template, setup e instruções.
tsconfig.app.json    # Configuração TypeScript para o bundle da aplicação.
tsconfig.json        # Config TypeScript base.
tsconfig.node.json   # Config TSC para scripts Node/devtools.
vite.config.ts       # Bundler/build tooling.
```

> **Regra:** tudo que for de domínio vive dentro de `src/features/<domínio>/<subárea>/…` com
> subpastas **components, hooks, pages, schemas, services, types, utils** e um `routes.tsx` (se
> houver rotas).

### Subestrutura por feature (contrato agnóstico)

```txt
src/features/<domínio>/<subárea>/
  components/                 # componentes expostos da subárea (cards, tables, forms, dialogs)
    <entidade>-*.tsx
  hooks/                      # React Query hooks por entidade/assunto
    <entidade>.keys.ts        # p.ex.: users.keys.ts
    use-<ação>-<entidade>.ts
    use-fetch-<entidade>s.ts
    use-fetch-<entidade>.ts
    use-create-<entidade>.ts
    use-update-<entidade>.ts
    use-delete-<entidade>.ts
    use-activate-<entidade>.ts
    use-inactivate-<entidade>.ts
    use-infinite-<entidade>s.ts
    use-block-<entidade>.ts
    use-unblock-<entidade>.ts
  pages/                      # páginas da subárea por entidade/cenário
    <entidade>-list.page.tsx
    <entidade>-detail.page.tsx
    <entidade>-new.page.tsx
    <entidade>-edit.page.tsx
  schemas/                    # zod (input/output) por caso de uso
    <entidade>.schema.ts
  services/                   # HTTP por entidade/assunto
    <entidade>.service.ts
  types/                      # tipos da subárea (TSDoc + @example)
    <entidade>.type.ts
  utils/                      # funções puras *somente* desta subárea – sempre *.util.ts
    <algo>.util.ts
  routes.tsx                  # (opcional) rotas da subárea
```

- **Nomes por entidade**: arquivos começam com o _slug_ da entidade (`user-*.page.tsx`,
  `users.keys.ts`).
- **Hooks (arquivos)**: **kebab-case iniciando com `use-`** (ex.: `use-fetch-users.ts`,
  `use-update-user.ts`, `use-block-user.ts`). `*.keys.ts` acompanha a entidade (ex.:
  `users.keys.ts`).
- **Utils (arquivos)**: sempre `*.util.ts` (ex.: `assets.util.ts`, `storage.util.ts`,
  `date.util.ts`).
- **Padrão de componente**: **Loading / Content / Exposed** (skeleton → formulário/tabela →
  orquestração).
- Componentes simples ficam em arquivos únicos dentro de `components/`. Só crie subpastas quando o
  componente tiver múltiplos arquivos complementares (ex.: DropdownMenu/DropdownMenuItem separados)
  e ambos são exportados.
- Services devem permanecer enxutos e focados por entidade. Separe arquivos quando lidar com
  contextos distintos (ex.: `payment-methods.service.ts`, `invoices.service.ts`,
  `prices.service.ts`).

### Internationalização (i18n)

1. **Configuração**
   - A configuração global vive em `src/lib/i18n.ts` (namespaces, detecção, fallback). Não
     reconfigure i18next fora desse arquivo.
   - `src/main.tsx` importa `@/lib/i18n` antes de renderizar a aplicação, garantindo recursos
     carregados.
   - JSONs em `public/locales/<lang>/<namespace>.json` devem manter as chaves em ordem alfabética.
     Prefira chaves planas (sem prefixos artificiais). Aninhe apenas quando for uma coleção de
     opções reutilizáveis (ex.: `status: { active, inactive }` ou mapas de select).
   - `common.json` concentra ações genéricas (`save`, `cancel`, `retry`, `close` etc.) e serve como
     fallback automático; reaproveite labels existentes antes de criar novas chaves.

2. **Uso nas features**
   - Use `useTranslation('<namespace>')` e, só se houver repetição real, aplique `keyPrefix`. Evite
     cascatas de prefixos como `tableFoo`/`formFoo` se as chaves planas já existirem.
   - Todas as strings exibidas devem vir das chaves; nada hardcoded.
   - Formatação (datas, horas, moedas) via utils dedicados (`date.util.ts`, `currency.util.ts` etc.)
     que recebem o `language` corrente; `react-intl` apenas para formatações avançadas.

**Exemplo de Implementação:**

```tsx
// src/features/<domínio>/<subárea>/components/<entidade>-header.tsx
import { useTranslation } from '@/hooks/use-translation';

export function EntityHeader() {
  const { t } = useTranslation('<domínio>', 'subarea.entity');
  return (
    <div>
      <h1>{t('title')}</h1>
      <p>{t('description')}</p>
    </div>
  );
}
```

### Types

1. **Onde vivem e para que servem**
   - `src/features/<domínio>/<subárea>/types/` guarda literais e aliases que representam
     comportamentos de runtime (status, gatilhos, roles, permissões) e não devem ser reescritos em
     cada schema. Use sufixo `.type.ts` por assunto (`entity-status.type.ts`,
     `entity-trigger.type.ts`). Evite arquivos monólitos misturando múltiplos assuntos.
   - `src/types/` é reservado para tipos globais realmente compartilhados entre features e que não
     têm equivalência direta em um schema. Se um type existir apenas para descrever a resposta de um
     endpoint já coberto pelo schema, remova-o e use `z.infer`.

2. **Estrutura do arquivo**
   - Inicie com um docblock explicando cada literal disponível.
   - Declare o array literal com `as const` e exporte o respectivo `type`
     (`export type EntityStatus = (typeof ENTITY_STATUSES)[number];`).
   - Mantenha cada arquivo focado: se precisar de mais de um enum/literal, crie um arquivo por
     assunto (`status`, `trigger`, `recipient`). Isso facilita tree-shaking e mapping no UI.

3. **Consumo**
   - Schemas devem importar esses arrays para validar campos (`z.enum(ENTITY_STATUSES)`).
   - Componentes, utils e mock API reutilizam a mesma constante para badges, filtros, seeds e
     geração de dados. Assim, não existem duas fontes de verdade para o mesmo literal.

**Exemplo de Implementação:**

```ts
// src/features/<domínio>/<subárea>/schemas/entity.schema.ts
import { z } from 'zod';

import { ENTITY_STATUSES } from '../types/entity-status.type';
// src/features/<domínio>/<subárea>/components/entity-status-badge.tsx
import type { EntityStatus } from '../types/entity-status.type';

// src/features/<domínio>/<subárea>/types/entity-status.type.ts
/**
 * Lista de possíveis status do ciclo de vida da entidade.
 *
 * - 'draft': Registro ainda não publicado.
 * - 'active': Disponível para uso.
 * - 'archived': Descontinuado.
 */
export const ENTITY_STATUSES = ['draft', 'active', 'archived'] as const;
export type EntityStatus = (typeof ENTITY_STATUSES)[number];

export const EntitySchema = z.object({
  id: z.string(),
  status: z.enum(ENTITY_STATUSES),
});

const STATUS_VARIANTS: Record<EntityStatus, string> = {
  draft: 'secondary',
  active: 'success',
  archived: 'muted',
};
```

### Schemas

1. **Organização**
   - Agrupe as validações por entidade (`entity.schema.ts`, `entity-item.schema.ts`) e exponha um
     `EntitySchema` canônico por arquivo.
   - Use sufixos para entradas/consultas (`EntityCreateSchema`, `EntityUpdateSchema`,
     `EntityQuerySchema`) e mantenha os retornos alinhados ao schema base
     (`type EntitySchema = z.output<typeof entitySchema>`).

2. **Variações**
   - Derive versões com `pick/omit/partial` antes de adicionar campos extras; as validações
     atomizadas vivem em `schemas/primitives.ts` (identificadores, e-mails, enums).
   - Se o mesmo shape atender create/update, nomeie como `EntityUpsertSchema` (com
     `type EntityUpsertSchemaIn = z.input<typeof EntityUpsertSchema>`). Só use `Create`/`Update`
     quando houver diferenças reais.
   - Tipos de entrada expostos terminam com `Schema` (`EntityCreateSchema`); retornos reutilizam o
     nome sem sufixo (`EntitySchema`, `EntityListItemSchema`).

3. **Ordem do arquivo**
   - Após os imports, defina consts internas (`const entitySchema = z.object({ … })`), depois
     exporte (`export const EntitySchema = entitySchema`). Não deixe helpers não utilizados;
     mantenha tudo inline.
   - Sempre importe `{ z }` de `zod` e reutilize o `errorMap` global; evite mensagens hardcoded.

**Exemplo de Implementação:**

```ts
// src/features/<domínio>/<subárea>/schemas/<entidade>.schema.ts
import { z } from 'zod';

import { IdentifierField, NameField } from './primitives';

const entitySchema = z
  .object({
    id: IdentifierField,
    name: NameField,
  })
  .strict();

export type EntitySchema = z.output<typeof entitySchema>;

export const EntityCreateSchema = entitySchema.omit({ id: true });
export type EntityCreateSchemaIn = z.input<typeof EntityCreateSchema>;
```

> O `src/lib/zod.ts` define o `errorMap` usado globalmente para mensagens de validação; ele é
> importado em `src/main.tsx`. Evite definir mensagens diretamente nos schemas, a menos que seja uma
> exceção realmente personalizada.

### Validações

- Utilize exclusivamente **Zod** para validar entradas de formulários antes de enviar ao backend. Os
  schemas devem viver em `src/features/<domínio>/<subárea>/schemas/` e serem consumidos via
  `zodResolver`.
- Evite validações manuais em hooks/services; toda regra de campo (required, tamanho, formatos)
  precisa estar declarada no schema e sincronizada com o form.
- Regras de negócio mais complexas (ex.: combinações de campos, integrações externas) devem ser
  validadas no backend. Apenas validações leves adicionais podem ocorrer no service (ex.: checagem
  de estado antes de enviar).
- Sempre derive `type FooSchemaIn = z.input<typeof FooSchema>` e use esse tipo na camada de
  form/hooks para garantir que a validação compile.

**Exemplo de Implementação:**

```tsx
// src/features/<domínio>/<subárea>/schemas/<entidade>.schema.ts
import { useForm } from 'react-hook-form';

// src/features/<domínio>/<subárea>/components/<entidade>-form.tsx
import { zodResolver } from '@hookform/resolvers/zod';
import { z } from 'zod';

import { useCreateEntity } from '../hooks/use-create-entity';
import { EntityCreateSchema, type EntityCreateSchemaIn } from '../schemas/<entidade>.schema';
import { EmailField, NameField } from './primitives';

const EntityCreateSchema = z.object({
  name: NameField.min(2),
  email: EmailField,
  enabled: z.boolean().default(true),
});

export type EntityCreateSchemaIn = z.input<typeof EntityCreateSchema>;

const form = useForm<EntityCreateSchemaIn>({
  resolver: zodResolver(EntityCreateSchema),
});
```

### Services

1. **Estrutura**
   - Nomeie os arquivos por entidade (`entity.service.ts`, `entity-item.service.ts`) e mantenha
     apenas as operações daquela entidade.
   - Evite reexportar services via `index.ts`; cada hook/componente importa apenas o arquivo
     necessário.

2. **Contratos**
   - Inputs sempre usam os schemas definidos na seção _Schemas_ (`EntityCreateSchemaIn`,
     `EntityUpdateSchemaIn`).
   - Endpoints de leitura retornam dados (`getEntity`, `getEntities`, `getEntitiesPaged`). Mutations
     retornam `void` salvo exceções descritas no projeto.

3. **Padrão REST**
   - Funções seguem `getEntity(id)`, `getEntities()`, `getEntitiesPaged(params)`,
     `createEntity(payload)`, `updateEntity(payload)`, `deleteEntity(id)`.
   - Normalize payloads antes do request (ex.: criar `owner` a partir de `ownerName/ownerEmail`).
   - Base paths devem iniciar com `/` (ex.: `/v1/entities/...`).

**Exemplo de Implementação:**

```ts
// src/features/<domínio>/<subárea>/services/entity.service.ts
import { api } from '@/lib/http';

import type {
  EntityCreateSchemaIn,
  EntityPageSchema,
  EntityQuerySchema,
  EntitySchema,
  EntityUpdateSchemaIn,
} from '../schemas/entity.schema';

const BASE_PATH = '/v1/entities';

export function getEntitiesPaged(params: EntityQuerySchema) {
  return api.getPaged<EntityPageSchema>(BASE_PATH, params);
}

export function getEntity(id: string) {
  return api.get<EntitySchema>(`${BASE_PATH}/${id}`);
}

export function createEntity(payload: EntityCreateSchemaIn) {
  return api.post<void>(BASE_PATH, normalizePayload(payload));
}

export function updateEntity(id: string, payload: EntityUpdateSchemaIn) {
  return api.patch<void>(`${BASE_PATH}/${id}`, normalizePayload(payload));
}

export function deleteEntity(id: string) {
  return api.delete<void>(`${BASE_PATH}/${id}`);
}

function normalizePayload(payload: EntityCreateSchemaIn | EntityUpdateSchemaIn) {
  const { ownerName, ownerEmail, ...rest } = payload;
  return {
    ...rest,
    owner: {
      name: ownerName,
      email: ownerEmail,
    },
  };
}
```

### Hooks

- Nomeie arquivos em kebab-case iniciando com `use-` e exporte apenas a função principal (ex.:
  `use-fetch-entities.ts` => `useFetchEntities`).
- Hooks globais (`src/hooks/`) concentram comportamentos reutilizáveis entre features (ex.:
  `useBreakpoint`, `useClipboard`).
- Hooks de feature vivem em `src/features/<domínio>/<subárea>/hooks/` e encapsulam React Query,
  estados derivados e integrações dessa subárea. IDs e parâmetros obrigatórios são recebidos
  diretamente pela função; apenas opções/comportamentos extras (callbacks, query params opcionais)
  ficam no objeto de opções.
- Sempre derive os tipos a partir dos schemas/services (`EntityCreateSchemaIn`, `EntityListSchema`)
  e use os query keys dedicados da subárea (`entity.keys.ts`) para manter cache consistente.
- **Mutations**:
  - Tipos internos seguem o padrão `HookVariables` (dados recebidos pelo `mutationFn`) e
    `HookOptions` (callbacks obrigatórios `onSuccess` e `onError` para possibilitar feedback ao
    usuário; ambos são repassados diretamente para `useMutation` para evitar wrappers).
  - Retorne helpers semânticos (`createEntity`, `createEntityAsync`, `isCreatingEntity`,
    `createEntityError`) em vez de expor o objeto do React Query.
  - IDs necessários para a operação devem fazer parte da assinatura do hook
    (`useUpdateEntity(entityId, options)`).
- **Queries**:
  - Determine internamente (`const shouldFetch = Boolean(entityId)`), deixando claro quando a
    requisição deve disparar.
  - Para listas e paginações, normalize o retorno (ex.: `items`, `total`, `page`, `pageSize`,
    `hasNextPage`) para que componentes não precisem conhecer React Query.
  - Queries infinitas adicionam um escopo claro na chave
    (`[...entityKeys.lists(), 'infinite', params]`) e usam helpers como `getNextPage`.
- Sempre forneça ganchos específicos (fetch detail, fetch list paginada, fetch infinita, mutations
  de create/update/delete) ao invés de reusar um hook genérico. Isso mantém o consumo previsível e
  condizente com o domínio.

**Exemplo de Implementação:**

```ts
// src/features/<domínio>/<subárea>/hooks/entity.keys.ts

// src/features/<domínio>/<subárea>/hooks/use-fetch-entity.ts

// src/features/<domínio>/<subárea>/hooks/use-fetch-entities.ts

// src/features/<domínio>/<subárea>/hooks/use-infinite-entities.ts

// src/features/<domínio>/<subárea>/hooks/use-create-entity.ts

// src/features/<domínio>/<subárea>/hooks/use-update-entity.ts
import {
  useInfiniteQuery,
  useMutation,
  useMutation,
  useQuery,
  useQuery,
  useQueryClient,
  useQueryClient,
} from '@tanstack/react-query';

import { getNextPage, getPagedData } from '@/utils/pagination.util';

import type {
  EntityCreateSchemaIn,
  EntityListSchema,
  EntityPageSchema,
  EntityPageSchema,
  EntityQuerySchema,
  EntityQuerySchema,
  EntityQuerySchema,
  EntitySchema,
  EntityUpdateSchemaIn,
} from '../schemas/entity.schema';
import {
  createEntity,
  getEntitiesPaged,
  getEntitiesPaged,
  getEntity,
  updateEntity,
} from '../services/entity.service';
import { entityKeys, entityKeys, entityKeys, entityKeys, entityKeys } from './entity.keys';

const ENTITY_SCOPE = ['<domínio>', '<subárea>'] as const;

export const entityKeys = {
  all: ENTITY_SCOPE,
  lists: () => [...ENTITY_SCOPE, 'list'] as const,
  list: (params: EntityQuerySchema) => [...entityKeys.lists(), params] as const,
  detail: (id: string) => [...ENTITY_SCOPE, 'detail', id] as const,
  activity: (id: string) => [...ENTITY_SCOPE, 'activity', id] as const,
} as const;

export function useFetchEntity(entityId: string) {
  const shouldFetch = Boolean(entityId);

  return useQuery<EntitySchema, Error>({
    queryKey: entityKeys.detail(entityId),
    queryFn: () => getEntity(entityId),
    enabled: shouldFetch,
    staleTime: 300_000,
  });
}

export function useFetchEntities(params: EntityQuerySchema) {
  const query = useQuery<EntityPageSchema, Error>({
    queryKey: entityKeys.list(params),
    queryFn: () => getEntitiesPaged(params),
    placeholderData: (previous) => previous,
    staleTime: 60_000,
  });

  return {
    page: query.data,
    items: query.data?.items ?? [],
    isLoadingEntities: query.isLoading,
    fetchEntitiesError: query.error,
    refetchEntities: query.refetch,
  };
}

export function useInfiniteEntities(params: EntityQuerySchema) {
  const baseParams: EntityQuerySchema = {
    ...params,
    page: params.page ?? 1,
    pageSize: params.pageSize ?? 20,
  };

  const query = useInfiniteQuery<EntityPageSchema, Error>({
    queryKey: [...entityKeys.lists(), 'infinite', baseParams],
    queryFn: ({ pageParam = baseParams.page }) =>
      getEntitiesPaged({ ...baseParams, page: pageParam }),
    initialPageParam: baseParams.page,
    getNextPageParam: getNextPage,
  });

  const { rows, total } = getPagedData(query.data?.pages);

  return {
    items: rows,
    total,
    fetchNextPage: query.fetchNextPage,
    hasNextPage: query.hasNextPage ?? false,
    isFetchingNextPage: query.isFetchingNextPage,
    isLoadingEntities: query.isPending,
    fetchEntitiesError: query.error,
  };
}

type HookOptions = {
  onSuccess: (variables: EntityCreateSchemaIn) => void;
  onError: (error: unknown, variables: EntityCreateSchemaIn) => void;
};

export function useCreateEntity({ onSuccess, onError }: HookOptions) {
  const queryClient = useQueryClient();

  const mutation = useMutation<void, unknown, EntityCreateSchemaIn>({
    mutationFn: (payload) => createEntity(payload),
    onSuccess: async (_, variables) => {
      await queryClient.invalidateQueries({ queryKey: entityKeys.lists() });
      onSuccess(variables);
    },
    onError,
  });

  return {
    createEntity: mutation.mutate,
    createEntityAsync: mutation.mutateAsync,
    isCreatingEntity: mutation.isPending,
    createEntityError: mutation.error,
  };
}

type HookOptions = {
  onSuccess: (variables: EntityUpdateSchemaIn) => void;
  onError: (error: unknown, variables: EntityUpdateSchemaIn) => void;
};

export function useUpdateEntity(entityId: string, { onSuccess, onError }: HookOptions) {
  const queryClient = useQueryClient();

  const mutation = useMutation<void, unknown, HookVariables>({
    mutationFn: (input) => updateEntity(entityId, input),
    onSuccess: async (_, variables) => {
      await Promise.all([
        queryClient.invalidateQueries({ queryKey: entityKeys.detail(entityId) }),
        queryClient.invalidateQueries({ queryKey: entityKeys.lists() }),
      ]);
      onSuccess(variables);
    },
    onError,
  });

  return {
    updateEntity: mutation.mutate,
    updateEntityAsync: mutation.mutateAsync,
    isUpdatingEntity: mutation.isPending,
    updateEntityError: mutation.error,
  };
}
```

### Tema

1. **Stack**
   - Usamos `next-themes` com `class` no `<html>` para alternar entre `light | dark | system`.
   - Tokens ficam em `src/styles` (Tailwind 4). Nunca force cores diretamente; use tokens/variáveis.

2. **Boas práticas**
   - Qualquer componente que lide com cores precisa respeitar as classes `dark:` do Tailwind.
   - Evite inline styles que dependam de variações de tema; prefira utilitários.
   - Persistência do tema (localStorage) já é tratada pelo provider de tema; não reimplemente
     lógica.

3. **Reuso**
   - Componentes globais devem ser testados nos dois temas antes do PR.
   - Se precisar expor um seletor de tema, reutilize os hooks/helpers do provider em
     `src/providers/theme-provider`.

### Feedback (notificações)

- Utilize `src/lib/notify.tsx` para todo feedback visual ao usuário (sucesso, erro, info, warning,
  loading, fatal).
- Mensagens devem ser traduzidas antes de chamar `notify`; mantenha-as curtas e objetivas.
- Nunca chame `notify` dentro de hooks/services. Os componentes (camada de UI) são os responsáveis
  por informar o usuário.
- Prefira `notify.loading` + `notify.update/dismiss` para fluxos longos, e use `description` para
  detalhes quando necessário.

**Exemplo**

```tsx
async function handleSubmit(values: EntityCreateSchemaIn) {
  try {
    notify.loading(t('entitySaving'));
    await createEntity(values);
    notify.success(t('entityCreateSuccess'));
  } catch (error) {
    notify.error(t('entityCreateError'), { description: (error as Error).message });
  }
}
```

### Componentes

1. **Escopo**
   - `src/components/ui/`: componentes puros (shadcn/ui) reutilizados em qualquer lugar.
   - `src/components/` (demais arquivos): componentes globais de domínio leve (form helpers, estados
     vazios) usados por qualquer feature.
   - `src/features/<domínio>/<subárea>/components/`: componentes específicos da feature. Aqui
     aplicamos o padrão CCL.

2. **Padrão CCL (Container / Content / Loading)**
   - **Container** (público): orquestra dados/mutações, decide entre Loading/Content e é o único
     exportado.
   - **Content** (privado): recebe `props` tipadas, renderiza UI e dispara callbacks.
   - **Loading** (privado): apenas skeletons/placeholders.

3. **Regras**
   - Arquivos em kebab-case (`entity-card.tsx`), nomes de componentes em PascalCase.
   - `Content` não chama hooks de dados; recebe tudo via props.
   - Skeletons simples, sem lógica.

**Exemplo de Implementação:**

```tsx
// src/features/<domínio>/<subárea>/components/<entidade>-card.tsx
import { useCallback } from 'react';

import { Button } from '@/components/ui/button';
import { Skeleton } from '@/components/ui/skeleton';

import { useUpdateEntity } from '../hooks/use-update-entity';

type EntityCardProps = {
  value: Entity;
};

type EntityCardContentProps = {
  value: Entity;
  pending: boolean;
  onSubmit: (value: Entity) => Promise<void>;
};

export function EntityCard({ value }: EntityCardProps) {
  const { isPending, updateEntity } = useUpdateEntity();

  const handleSubmit = useCallback(
    async (next: Entity) => {
      await updateEntity(next);
    },
    [updateEntity],
  );

  if (isPending) return <EntityCardLoading />;

  return <EntityCardContent value={value} pending={isPending} onSubmit={handleSubmit} />;
}

function EntityCardContent({ value, pending, onSubmit }: EntityCardContentProps) {
  const submit = useCallback(() => onSubmit({ ...value }), [onSubmit, value]);

  return (
    <div className="rounded-xl border p-4">
      <div className="flex justify-end">
        <Button type="button" onClick={submit} disabled={pending}>
          {pending ? 'Salvando…' : 'Salvar'}
        </Button>
      </div>
    </div>
  );
}

function EntityCardLoading() {
  return (
    <div className="rounded-xl border p-4">
      <Skeleton className="h-4 w-24 mb-2" />
      <Skeleton className="h-3 w-40" />
      <div className="mt-6 flex justify-end">
        <Skeleton className="h-10 w-28" />
      </div>
    </div>
  );
}
```

### Formulários

1. **Schema primeiro**
   - Crie o schema com `{ z }` de `zod`, derive `type FooSchema = z.output<typeof FooSchema>` e
     `type FooSchemaIn = z.input<typeof FooSchema>`.
   - Mensagens de erro vêm do `errorMap` global; use apenas chaves i18n (nada hardcoded).
   - Centralize campos compartilhados em `schemas/primitives.ts`.
   - Validações de e-mail, senha, CPF/CNPJ, etc. ficam centralizadas em `schemas/primitives.ts`.

2. **Setup do formulário**
   - Utilize `react-hook-form` com `zodResolver(schema)` e campos reutilizáveis (`InputField`,
     `SelectField`, `SwitchField`, etc.) e devem aceitar `name`, `label`, `control` (do RHF),
     `placeholder`, `helperText` e props extras.
   - Default values devem vir de builders puros (`buildDefaultValues(entity?)`) para evitar
     `undefined`.
   - Submits chamam mutations/services e propagam feedback via `notify.success/error`.

3. **UX e A11y**
   - Inputs precisam de `aria-invalid`, descrições e estados `disabled` enquanto houver `pending`.
   - Sempre mostre erros inline + toast (quando aplicável) e foque o primeiro campo inválido
     (`form.setFocus`).
   - Use `useTranslation('ns', 'keyPrefix')` para labels/placeholders.

**Exemplo de Implementação:**

```tsx
// src/features/<domínio>/<subárea>/components/<entidade>-form.tsx
import { useMemo } from 'react';
import { useForm } from 'react-hook-form';

import { zodResolver } from '@hookform/resolvers/zod';

import { notify } from '@/lib/notify';
import { Button } from '@/components/ui/button';
import { Form } from '@/components/ui/form';
import { InputField } from '@/components/form/input-field';
import { SelectField } from '@/components/form/select-field';
import { SwitchField } from '@/components/form/switch-field';
import { useTranslation } from '@/hooks/use-translation';

import { useCreateEntity } from '../hooks/use-create-entity';
import { EntityCreateSchema, type EntityCreateSchemaIn } from '../schemas/entity.schema';

type EntityFormProps = {
  entityId: string;
};

export function EntityForm({ entityId }: EntityFormProps) {
  const { t } = useTranslation('<domínio>', '<subárea>');
  const { isPending, createEntity } = useCreateEntity({ entityId });
  const form = useForm<EntityCreateSchemaIn>({
    resolver: zodResolver(EntityCreateSchema),
    defaultValues: useMemo(() => ({ name: '', type: 'primary', enabled: true }), []),
  });

  const typeOptions = [
    { value: 'primary', label: t('typePrimary') },
    { value: 'secondary', label: t('typeSecondary') },
    { value: 'custom', label: t('typeCustom') },
  ];

  async function handleSubmit(values: EntityCreateSchemaIn) {
    try {
      await createEntity(values);
      notify.success(t('entityCreateSuccess'));
      form.reset({ name: '', type: values.type, enabled: values.enabled });
    } catch (error) {
      notify.error(t('entityCreateError'));
      const firstError = Object.keys(form.formState.errors)[0];
      if (firstError) form.setFocus(firstError as keyof EntityCreateSchemaIn);
    }
  }

  return (
    <Form {...form}>
      <form
        className="grid gap-4 sm:grid-cols-[minmax(0,2fr)_minmax(0,1fr)_auto]"
        onSubmit={form.handleSubmit(handleSubmit)}
        noValidate
      >
        <InputField
          control={form.control}
          name="name"
          label={t('name')}
          placeholder={t('namePlaceholder')}
          disabled={isPending}
        />
        <SelectField
          control={form.control}
          name="type"
          label={t('type')}
          options={typeOptions}
          disabled={isPending}
        />
        <SwitchField
          control={form.control}
          name="enabled"
          label={t('enabled')}
          disabled={isPending}
        />
        <Button type="submit" disabled={isPending}>
          {isPending ? t('entitySaving') : t('entitySave')}
        </Button>
      </form>
    </Form>
  );
}
```

### Tabelas (DataTable)

1. **Estrutura**
   - Toda tabela complexa o componente global `DataTable` (TanStack Table + UI padrão) e fica em
     `src/features/<domínio>/<subárea>/components/<entidade>-table.tsx`.
   - Colunas vivem em `components/<entidade>-columns.ts` com `accessorKey`/`cell` puros; nunca
     declare colunas inline no container.
   - Filtros/paginação/ordenação são controlados via URL (`qs`) para manter estado compartilhável.

2. **Hooks e dados**
   - Queries paginadas usam `useInfiniteQuery` ou `useFetch<Entity>s(params)` com
     `keepPreviousData`.
   - Mantenha builders utilitários (`build<Entity>Filters`) em `utils/` para serializar
     filtros/ordenar.
   - Sempre exponha `total`, `page`, `pageSize` e `items` para o componente consumir; evite que a
     tabela conheça `react-query`.

3. **UX**
   - Reutilize células/ações comuns (badge de status, avatar, menu de ações).
   - Inclua estados de carregamento (`Skeleton`) declarando `meta.skeleton` nas colunas, além de
     vazio e erro consistentes com o DataTable.
   - Use `aria-sort`, `aria-label` e botões com ícones + textos para acessibilidade.

**Exemplo de Implementação:**

```tsx
// src/features/<domínio>/<subárea>/components/<entidade>-columns.ts
import type { ColumnDef } from '@tanstack/react-table';

import { Skeleton } from '@/components/ui/skeleton';
// src/features/<domínio>/<subárea>/components/<entidade>-table.tsx
import { DataTable } from '@/components/data-table';
import { useTranslation } from '@/hooks/use-translation';

import type { EntityListItem } from '../schemas/entity.schema';
import { entityColumns } from './<entidade>-columns';

export const entityColumns: ColumnDef<EntityListItem>[] = [
  {
    accessorKey: 'name',
    header: 'Name',
    meta: { skeleton: <Skeleton className="h-5 w-40" /> },
  },
  {
    accessorKey: 'status',
    header: 'Status',
    meta: { skeleton: <Skeleton className="h-5 w-20" /> },
    cell: ({ row }) => <StatusBadge status={row.original.status} />,
  },
  {
    id: 'actions',
    meta: { skeleton: <Skeleton className="h-5 w-12" /> },
    cell: ({ row }) => <EntityActions entity={row.original} />,
  },
];

type EntityTableProps = {
  items: EntityListItem[];
  total: number;
  page: number;
  pageSize: number;
  loading: boolean;
  onPageChange: (next: number) => void;
};

export function EntityTable({
  items,
  total,
  page,
  pageSize,
  loading,
  onPageChange,
}: EntityTableProps) {
  const { t } = useTranslation('<domínio>', '<subárea>');

  return (
    <DataTable
      columns={entityColumns}
      data={items}
      loading={loading}
      emptyState={{ title: t('emptyTitle'), description: t('emptyDescription') }}
      pagination={{
        total,
        page,
        pageSize,
        onPageChange,
      }}
    />
  );
}
```

### Filtros de Pesquisa

1. **Componente dedicado**
   - Cada entidade expõe um componente
     `src/features/<domínio>/<subárea>/components/<entidade>-filters.tsx`.
   - A página entrega apenas um objeto de estado `{ query, status, ... }` via `value` + um
     `onChange` único; o componente emite o próximo estado consolidado.
   - Use tipos derivados dos literais (`StatusFilter = EntityStatus | 'all'`) em `types/` para
     evitar repetir unions `| 'all'` pelo código.

2. **Subcomponentes internos**
   - Crie pequenos helpers internos (`StatusFilter`, `CategoryFilter`, `QueryFilter`) responsáveis
     por suas opções/labels.
   - Cada helper recebe somente `{ value, onChange }` para manter a semântica clara e permite
     mover/remoção fácil.
   - Utilize os componentes globais (`SearchField`, `Select`, `Button`) diretamente, sem wrappers
     genéricos.

3. **Ordenação semântica**
   - Ordem esperada: contador (total encontrado) → busca textual → selects
     (categoria/feature/status) → ações (reset).
   - Labels, placeholders e tooltips devem vir de `useTranslation('<domínio>', '<subárea>')`; evite
     hardcodes.

4. **Exemplo**

```tsx
// src/features/admin/parameters/components/parameter-filters.tsx
type ParameterFilterState = {
  query: string;
  category: ParameterCategoryFilter;
  feature: ParameterFeatureFilter;
  editable: EditableFilter;
};

export function ParameterFilters({ value, total, onChange }: ParameterFiltersProps) {
  const { t } = useTranslation('admin', 'parameters');

  const emitChange = useCallback(
    (next: Partial<ParameterFilterState>) => onChange({ ...value, ...next }),
    [value, onChange],
  );

  return (
    <div className="rounded-xl border bg-card p-4">
      <div className="flex justify-between gap-4">
        <p className="text-sm font-medium text-muted-foreground">
          {typeof total === 'number'
            ? t('filters.total', { count: total })
            : t('filters.totalFallback')}
        </p>
        <div className="flex flex-wrap items-end gap-3">
          <QueryFilter value={value.query} onChange={(query) => emitChange({ query })} />
          <CategoryFilter
            value={value.category}
            onChange={(category) => emitChange({ category })}
          />
          <FeatureFilter value={value.feature} onChange={(feature) => emitChange({ feature })} />
          <EditableFilter
            value={value.editable}
            onChange={(editable) => emitChange({ editable })}
          />
          <Button variant="ghost" size="icon" onClick={() => onChange(DEFAULT_STATE)}>
            <Eraser className="size-4" />
          </Button>
        </div>
      </div>
    </div>
  );
}
```

> Quando precisar remover/adicionar um filtro, basta atuar dentro do componente; a página continua
> sem conhecer detalhes específicos.

### Rotas

1. **Estrutura por feature**
   - Cada subárea expõe `src/features/<domínio>/<subárea>/routes.tsx`, exportando um componente
     `FooRoutes`.
   - Dentro dele, usamos `<Routes>` + `<Route>` e aplicamos o layout adequado (ex.:
     `<DefaultLayout />`) como wrapper, mantendo paths relativos (`<Route path="/overview" />`
     etc.).

2. **Registro na raiz**
   - O agregador `src/routing/app-routing-setup.tsx` importa cada `FooRoutes` e os monta por prefixo
     (`<Route path="admin/*" element={<AdminRoutes />} />`).
   - Rotas globais (home, fallback, troca de layout) também ficam nesse arquivo para garantir
     consistência.

**Exemplo de Implementação:**

```tsx
// src/features/<domínio>/<subárea>/routes.tsx
// src/routing/app-routing-setup.tsx
import { Navigate, Route, Route, Routes, Routes } from 'react-router-dom';

import { DefaultLayout, DefaultLayout } from '@/layouts/default-layout';
import { HomePage } from '@/pages/home.page';

import { EntityRoutes } from '@/features/<domínio>/<subárea>/routes';

import { EntityDetailPage } from './pages/<entidade>-detail.page';
import { EntityListPage } from './pages/<entidade>-list.page';

export function EntityRoutes() {
  return (
    <Routes>
      <Route element={<DefaultLayout />}>
        <Route path="/lista" element={<EntityListPage />} />
        <Route path="/detalhe/:id" element={<EntityDetailPage />} />
      </Route>
    </Routes>
  );
}

export function AppRoutingSetup() {
  return (
    <Routes>
      <Route element={<DefaultLayout />}>
        <Route path="/" element={<HomePage />} />
      </Route>
      <Route path="<domínio>/*" element={<EntityRoutes />} />
      <Route path="*" element={<Navigate to="/" replace />} />
    </Routes>
  );
}
```

### Mock API

1. **Estrutura**
   - Dados vivem em `mock-api/db/*.json`. Cada arquivo corresponde a uma entidade
     (`entities-01.json`, `entities-02.json`).
   - Rotas personalizadas ficam em `mock-api/resources/*.routes.cjs`, espelhando a API real.

2. **Padrões de dados**
   - IDs semânticos (`entity_01`, `entity_02`) e campos coerentes com os schemas (datas
     consistentes, relacionamentos válidos).
   - Variedade suficiente para cobrir cenários de sucesso, erro, listas vazias, paginação, etc.

3. **Rotas**
   - Para um novo endpoint, crie uma rota REST no arquivo correspondente (`GET /v1/entities`,
     `POST /v1/entities`).
   - Utilize helpers do json-server (filtrar, ordenar, paginar) e normalize os payloads para
     refletir o backend futuro.

**Exemplo de Implementação:**

```js
// mock-api/resources/entities.routes.cjs
module.exports = (router) => {
  router.get('/v1/entities', (req, res, next) => {
    // suporte a paginação ?page=1&pageSize=10
    next();
  });

  router.post('/v1/entities', (req, res) => {
    const body = normalizeEntity(req.body);
    // inserir no db.json e responder com status 201
    res.status(201).json(body);
  });
};
```

### Testes end-to-end

1. **Organização**
   - Specs em `cypress/e2e/<domínio>/<página>.page.cy.ts`, espelhando a hierarquia das features.
   - Fixtures ficam em `cypress/fixtures/<domínio>/<página>/<cenário>.json|ts`.
   - Comandos utilitários moram em `cypress/support/commands/<assunto>.ts` com TSDoc e sem `any`.
   - Tasks Node-side em `cypress/support/tasks/*.task.ts`, expostas via
     `cypress/support/tasks/index.ts`.

2. **Escrita**
   - Use `context('should ... when ...')` para agrupar cenários; mensagens de `it` devem concluir a
     frase.
   - Nomeie fixtures em kebab-case (`valid-credentials.json`).
   - Evite hardcode: carregue dados com `cy.fixture()` e helpers (`cy.loginAsAdmin()`).

3. **Execução**
   - Limpe estado antes de cada teste (`cy.clearCookies`, `cy.clearLocalStorage`,
     `cy.clearMailbox`).
   - Use `cy.intercept` para observar requisições e garantir asserts.
   - Para e-mails, utilize as tasks (`mailbox:clear`, `mailbox:readLatest`).

4. **Scripts**
   - `pnpm cy:open` (modo interativo).
   - `pnpm test:e2e` (modo headless; requer app + mock rodando).

**Exemplo de Implementação:**

```ts
// cypress/e2e/<domínio>/<subárea>/<entidade>-list.page.cy.ts
describe('Entity list page', () => {
  beforeEach(() => {
    cy.clearCookies();
    cy.loginAsAdmin();
  });

  context('should display jobs list', () => {
    it('shows the first page when API succeeds', () => {
      cy.intercept('GET', '/v1/<domínio>/<subárea>*', {
        fixture: '<domínio>/<subárea>/list-success.json',
      }).as('fetchEntities');
      cy.visit('/<domínio>/<subárea>');
      cy.wait('@fetchEntities');
      cy.contains('Entity 01').should('be.visible');
    });
  });
});
```

### Estrutura de commits & PRs

1. **Branches**
   - Padrão: `git checkout -b <tipo>/<referência>-<descricao>` (ex.:
     `feature/task-101-envio-email`).
   - `tipo`: `feature`, `bugfix`, `hotfix`, `release`, `chore` etc.
   - `referência`: ID da task (ex.: `task-123`), seguido de uma descrição curta em kebab-case.

2. **Commits**
   - Use Conventional Commits: `feat`, `fix`, `docs`, `refactor`, `style`, `test`, `chore`.
   - Mensagens no formato `tipo(escopo): descrição` (ex.: `fix(auth): corrige fluxo de login`).
   - Agrupe alterações relacionadas em um commit limpo; evite commits WIP.

3. **Pull/Merge Request**
   - Após `git push`, abra uma MR/PR usando o template do projeto.
   - Checklist mínima:
     - [ ] `pnpm lint`, `pnpm format`, `pnpm typecheck` passaram.
     - [ ] Strings novas adicionadas nos arquivos de i18n.
     - [ ] Testes (unit/e2e) atualizados quando necessário.
     - [ ] Acessibilidade verificada (tab, foco, aria).
     - [ ] Sem dados sensíveis em logs/toasts.
