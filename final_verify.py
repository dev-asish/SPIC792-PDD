import onnx

model_path = "model_updated.onnx"  # Update path if needed

try:
    model = onnx.load(model_path)
    onnx.checker.check_model(model)
    print("✅ Model is valid and ready for integration!")
except Exception as e:
    print("❌ Model check failed:", e)
