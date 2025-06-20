import onnxruntime as ort
import numpy as np
import joblib
import pandas as pd
from sklearn.metrics import mean_absolute_error

# Load test dataset
df = pd.read_csv("vehicle_price.csv")

# Select test features (use the same ones as in training)
features = ["Vehicle_Type", "Brand", "Model", "Seating_Capacity", "Fuel_Type", "Mileage_kmpl", "NOx_Emissions", "CO2_Emissions"]
X = df[features].iloc[:5]  # Select a few test samples

# Load label encoders and scaler
encoders = joblib.load("encoder.pkl")
scaler = joblib.load("scaler.pkl")

# Encode categorical features
for col in ["Vehicle_Type", "Brand", "Model", "Fuel_Type"]:
    X[col] = encoders[col].transform(X[col])

# Normalize numerical features
X[["Mileage_kmpl", "NOx_Emissions", "CO2_Emissions"]] = scaler.transform(X[["Mileage_kmpl", "NOx_Emissions", "CO2_Emissions"]])

# Convert to numpy array
X_test_np = X.astype(np.float32).values

# Load ONNX model
onnx_session = ort.InferenceSession("vehicle_price_model.onnx")

# Get input name from ONNX model
input_name = onnx_session.get_inputs()[0].name

# Run ONNX inference
onnx_predictions = onnx_session.run(None, {input_name: X_test_np})[0]

# Load trained scikit-learn model
model = joblib.load("model.pkl")

# Get scikit-learn predictions
sklearn_predictions = model.predict(X_test_np)

# Compare predictions
mae = mean_absolute_error(sklearn_predictions, onnx_predictions)

# Print results
print("\n✅ ONNX Model Verification Completed!")
print("Scikit-learn Predictions:", sklearn_predictions)
print("ONNX Model Predictions:", onnx_predictions)
print("Mean Absolute Error (MAE):", mae)

# Check if the error is low
if mae < 1e-3:
    print("✅ ONNX Model is correctly converted! 🚀")
else:
    print("⚠️ Warning: ONNX Model has discrepancies. Check preprocessing steps!")
