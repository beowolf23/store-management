#!/bin/bash

# List of service directories
SERVICES=(
  "product-service"
  "inventory-service"
)

# Builder image
BUILDER="paketobuildpacks/builder-jammy-base"

# Build each service
for SERVICE in "${SERVICES[@]}"; do
  IMAGE_NAME="$SERVICE"  # You can prefix with a registry or custom name if needed

  echo "🔨 Building image for $SERVICE..."
  pack-cli build "$IMAGE_NAME" \
    --path "./$SERVICE" \
    --builder "$BUILDER" \
    --clear-cache

  if [ $? -ne 0 ]; then
    echo "❌ Failed to build $SERVICE"
    exit 1
  fi

  echo "✅ Successfully built $IMAGE_NAME"
done

echo "🏁 All services built successfully."
