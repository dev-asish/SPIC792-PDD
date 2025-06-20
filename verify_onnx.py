import onnx
import onnxruntime as ort
import numpy as np

# Load ONNX model
model_path = "vehicle_price_model.onnx"  # Replace with your ONNX model path
onnx_model = onnx.load(model_path)

# Check model validity
try:
    onnx.checker.check_model(onnx_model)
    print("ONNX model loaded and validated successfully!")
except onnx.onnx_cpp2py_export.checker.ValidationError as e:
    print("ONNX model validation failed:", e)
    exit()

# Load ONNX runtime session
session = ort.InferenceSession(model_path)

# Get input details
input_name = session.get_inputs()[0].name
input_shape = session.get_inputs()[0].shape
input_type = session.get_inputs()[0].type
print(f"Input Name: {input_name}, Shape: {input_shape}, Type: {input_type}")

# Generate dummy input
dummy_input = np.random.rand(*[dim if dim else 1 for dim in input_shape]).astype(np.float32)

# Run inference
onnx_output = session.run(None, {input_name: dummy_input})
print("\nONNX Model Output:\n", onnx_output[0])
