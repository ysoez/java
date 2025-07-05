# Load Testing

### Vegeta

```
brew update && brew install vegeta
echo "GET http://localhost:8888/asset" | vegeta attack -workers=4 -max-workers=10 -duration=30s | tee results.bin | vegeta report
```
