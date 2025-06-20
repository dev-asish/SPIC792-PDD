import pandas as pd
import numpy as np
import joblib
import onnx
import onnxruntime as ort
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import LabelEncoder, StandardScaler
from sklearn.ensemble import RandomForestRegressor
from skl2onnx import convert_sklearn
from skl2onnx.common.data_types import FloatTensorType

# 1️⃣ Load the Dataset
df = pd.read_csv("vehicle_price.csv")

# 2️⃣ Select Features and Target Variable
features = ["Vehicle_Type", "Brand", "Model", "Seating_Capacity", "Fuel_Type", "Mileage_kmpl", "NOx_Emissions", "CO2_Emissions"]
target = "Price"

X = df[features].copy()  # Avoid modifying original DataFrame
y = df[target]

# 3️⃣ Encode Categorical Features
encoders = {}
categorical_cols = ["Vehicle_Type", "Brand", "Model", "Fuel_Type"]

for col in categorical_cols:
    encoders[col] = LabelEncoder()
    X.loc[:, col] = encoders[col].fit_transform(X[col])  # Use .loc to prevent warnings

# 4️⃣ Normalize Numerical Features
scaler = StandardScaler()
numeric_cols = ["Mileage_kmpl", "NOx_Emissions", "CO2_Emissions"]
X.loc[:, numeric_cols] = scaler.fit_transform(X[numeric_cols])

# 5️⃣ Split Data into Train & Test Sets
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)

# 6️⃣ Train Random Forest Model
model = RandomForestRegressor(n_estimators=100, random_state=42)
model.fit(X_train, y_train)

# 7️⃣ Convert the Model to ONNX Format
initial_type = [("float_input", FloatTensorType([None, X_train.shape[1]]))]
onnx_model = convert_sklearn(model, initial_types=initial_type)
onnx.save_model(onnx_model, "vehicle_price_model.onnx")

# 8️⃣ Save the Encoders and Scaler for Future Use
joblib.dump(encoders, "encoder.pkl")
joblib.dump(scaler, "scaler.pkl")
joblib.dump(model, "model.pkl")  # Optional: Save sklearn model for direct use

print("✅ Model training & ONNX conversion completed!")
