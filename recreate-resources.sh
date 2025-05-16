
#!/bin/bash

COMPOSE_FILE="docker-compose.full.yml"
SERVICO_TESTE="kafka-ui"
PORTA_TESTE="8080"

GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[0;33m'
WHITE='\033[1;37m'
CYAN='\033[0;36m'
NC='\033[0m'

check_docker_running() {
  if ! docker info > /dev/null 2>&1; then
    echo -e "${RED}Docker não está em execução.${NC}"
    exit 1
  fi
}

stop_stack() {
  echo -e "${CYAN}[20%%] Parando serviços...${NC}"
  docker-compose -f "$COMPOSE_FILE" down --remove-orphans > /dev/null 2>&1
}

remove_volumes() {
  echo -e "${CYAN}[40%%] Limpando volumes...${NC}"
  docker volume prune -f > /dev/null
}

start_stack() {
  echo -e "${CYAN}[60%%] Subindo novamente...${NC}"
  docker-compose -f "$COMPOSE_FILE" up -d
}

is_stack_running() {
  docker inspect -f '{{.State.Running}}' "$(docker-compose -f "$COMPOSE_FILE" ps -q "$SERVICO_TESTE")" 2>/dev/null | grep -q "true"
}

get_ui_url() {
  docker-compose -f "$COMPOSE_FILE" port "$SERVICO_TESTE" "$PORTA_TESTE"
}

show_success() {
  echo -e "${CYAN}[90%%] Verificando serviços...${NC}"
  if is_stack_running; then
    local url=$(get_ui_url)
    echo -e "${GREEN}[100%%] Stack recriada com sucesso!${NC}"
    echo -e "${WHITE}Kafka UI disponível em: http://$url${NC}"
  else
    echo -e "${RED}Erro ao recriar a stack.${NC}"
    docker-compose -f "$COMPOSE_FILE" logs --tail=20
    exit 1
  fi
}

main() {
  echo -e "${YELLOW}Recriando a stack completa...${NC}"
  check_docker_running
  stop_stack
  remove_volumes
  start_stack
  sleep 4
  show_success
}

main
